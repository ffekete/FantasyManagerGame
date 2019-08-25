package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.selector.ClosestEnemySelector;
import com.mygdx.game.logic.selector.SelectionUtils;
import com.mygdx.game.item.weapon.RangedWeapon;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.CompoundActivity;
import com.mygdx.game.logic.activity.compound.MoveThenAttackActivity;
import com.mygdx.game.logic.activity.single.OffensiveSpellCastActivity;
import com.mygdx.game.logic.activity.single.PreCalculatedMovementActivity;
import com.mygdx.game.logic.activity.single.RangedAttackActivity;
import com.mygdx.game.logic.activity.single.SimpleAttackActivity;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.MapRegistry;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.common.util.MathUtil.distance;

public class MoveAndRangedAttackDecision implements Decision {

    private final ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    private final ActorMovementHandler actorMovementHandler = ActorMovementHandler.INSTANCE;
    private final MapRegistry mapRegistry = MapRegistry.INSTANCE;
    private final ClosestEnemySelector closestEnemySelector = new ClosestEnemySelector();

    @Override
    public boolean decide(Actor actor) {

        if (actor.isSleeping()) {
            return false;
        }

        if (actor.getRightHandItem() == null || !RangedWeapon.class.isAssignableFrom(actor.getRightHandItem().getClass())) {
            return false;
        }

        // already attacking, the decision chain should end here
        if (actor.getActivityStack().getCurrent().getMainClass().equals(SimpleAttackActivity.class) ||
                actor.getActivityStack().getCurrent().getMainClass().equals(OffensiveSpellCastActivity.class)) {
            return true;
        }

        Actor enemy = closestEnemySelector.find(actor, actorRegistry.getActors(actor.getCurrentMap()), Config.ATTACK_DISTANCE);
        if (enemy != null) {
            if (distance(actor.getCoordinates(), enemy.getCoordinates()) > actor.getAttackRange()) {

                actor.getActivityStack().clear();

                PathFinder pathFinder = mapRegistry.getPathFinderFor(actor.getCurrentMap());

                long startDate = System.currentTimeMillis();
                List<PathFinder.Node> path;
                if (PreCalculatedMovementActivity.class.isAssignableFrom(enemy.getActivityStack().getCurrent().getCurrentClass())) {

                    PreCalculatedMovementActivity movementActivity = ((PreCalculatedMovementActivity) enemy.getActivityStack().getCurrent().getCurrentActivity());
                    Point target;
                    if (movementActivity == null) {
                        target = enemy.getCoordinates();
                    } else {
                        target = Point.of(movementActivity.getTargetX(), movementActivity.getTargetY());
                    }

                    path = pathFinder.findAStar(actor.getCoordinates(), target);
                } else {
                    path = pathFinder.findAStar(actor.getCoordinates(), enemy.getCoordinates());
                }
                System.out.println("Pathfinding took " + (System.currentTimeMillis() - startDate));

                int halfWay = path.size() / 2;

                if (path.size() < actor.getAttackRange()) {
                    actor.getActivityStack().add(new RangedAttackActivity(actor, enemy));
                } else {
                    actorMovementHandler.clearPath(actor);
                    List<PathFinder.Node> actorPath = new ArrayList<>();
                    int start = halfWay;
                    int end = path.size();

                    // if enemy is already fighting
                    if (RangedAttackActivity.class.isAssignableFrom(enemy.getActivityStack().getCurrent().getCurrentClass())) {
                        start = 1;
                        end = path.size();
                    } else if (PreCalculatedMovementActivity.class.isAssignableFrom(enemy.getActivityStack().getCurrent().getCurrentClass())) {
                        start = 1;
                        end = path.size();
                    }
                    for (int i = start; i < end; i++) {
                        actorPath.add(path.get(i));
                    }

                    CompoundActivity compoundActivityForActor = new MoveThenAttackActivity(Config.Activity.MOVE_THEN_ATTACK_PRIORITY, SimpleAttackActivity.class);
                    compoundActivityForActor.add(new PreCalculatedMovementActivity(actor, actorPath));
                    compoundActivityForActor.add(new RangedAttackActivity(actor, enemy));
                    actor.getActivityStack().add(compoundActivityForActor);

                }

                return true;
            } else {
                actor.getActivityStack().add(new RangedAttackActivity(actor, enemy));
                return true;
            }

        }
        return false;
    }
}
