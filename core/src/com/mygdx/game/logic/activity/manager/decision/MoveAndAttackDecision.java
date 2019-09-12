package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
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
import com.mygdx.game.logic.selector.ClosestEnemySelector;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.MapRegistry;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.common.util.MathUtil.distance;

public class MoveAndAttackDecision implements Decision {

    private final ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    private final ActorMovementHandler actorMovementHandler = ActorMovementHandler.INSTANCE;
    private final MapRegistry mapRegistry = MapRegistry.INSTANCE;
    private final ClosestEnemySelector closestEnemySelector = new ClosestEnemySelector();

    @Override
    public boolean decide(Actor actor) {

        if(actor.isSleeping()) {
            return false;
        }

        // this will be handled by a different decision class
        if (actor.getRightHandItem() == null || RangedWeapon.class.isAssignableFrom(actor.getRightHandItem().getClass()))
            return false;

        // already attacking, the decision chain should end here
        if (actor.getActivityStack().contains(SimpleAttackActivity.class) ||
                actor.getActivityStack().contains(RangedAttackActivity.class) ||
                actor.getActivityStack().contains(OffensiveSpellCastActivity.class)) {
            return true;
        }

        Actor enemy = closestEnemySelector.find(actor, actorRegistry.getActors(actor.getCurrentMap()), Config.ATTACK_DISTANCE);

        if (enemy != null) {
            if (distance(actor.getCoordinates(), enemy.getCoordinates()) > actor.getAttackRange()) {

                //actor.getActivityStack().reset();

                PathFinder pathFinder = mapRegistry.getPathFinderFor(actor.getCurrentMap());

                long startDate = System.currentTimeMillis();
                List<PathFinder.Node> path;

                // if enemy is already moving
                if (PreCalculatedMovementActivity.class.isAssignableFrom(enemy.getActivityStack().getCurrent().getCurrentClass())) {

                    PreCalculatedMovementActivity movementActivity = ((PreCalculatedMovementActivity) enemy.getActivityStack().getCurrent().getCurrentActivity());

                    Point target = Point.of(movementActivity.getTargetX(), movementActivity.getTargetY());

                    path = pathFinder.findAStar(actor.getCoordinates(), target);
                } else {
                    path = pathFinder.findAStar(actor.getCoordinates(), enemy.getCoordinates());
                }
                System.out.println("Pathfinding took " + (System.currentTimeMillis() - startDate));

                int halfWay = path.size() / 2;

                if (path.size() - 1 <= actor.getAttackRange()) {
                    actor.getActivityStack().add(new SimpleAttackActivity(actor, enemy));
                } else {
                    // need to move closer
                    actorMovementHandler.clearPath(actor);
                    List<PathFinder.Node> actorPath = new ArrayList<>();
                    int start = isActorAlreadyFighting(enemy) ? 1 : halfWay;
                    int end = path.size();

                    for (int i = start; i < end; i++) {
                        actorPath.add(path.get(i));
                    }
                    //actorMovementHandler.registerActorPath(actor, actorPath);
                    CompoundActivity compoundActivityForActor = new MoveThenAttackActivity(Config.Activity.ATTACK_PRIORITY, SimpleAttackActivity.class);
                    compoundActivityForActor.add(new PreCalculatedMovementActivity(actor, actorPath));
                    compoundActivityForActor.add(new SimpleAttackActivity(actor, enemy));
                    actor.getActivityStack().add(compoundActivityForActor);
                }

                // if a melee enemy is not already fighting then give a path to this enemy as well to the actor
                if ((enemy.getRightHandItem() != null && !RangedWeapon.class.isAssignableFrom(enemy.getRightHandItem().getClass())) &&
                        (!enemy.getActivityStack().getCurrent().getMainClass().equals(SimpleAttackActivity.class)
                                && !enemy.getActivityStack().contains(OffensiveSpellCastActivity.class))) {

                    //enemy.getActivityStack().reset();
                    if (path.size() - 1 < enemy.getAttackRange()) {
                        enemy.getActivityStack().add(new SimpleAttackActivity(enemy, actor));
                    } else {
                        actorMovementHandler.clearPath(enemy);
                        List<PathFinder.Node> enemyPath = new ArrayList<>();

                        for (int i = halfWay - 1; i >= 0; i--) {
                            enemyPath.add(path.get(i));
                        }

                        //actorMovementHandler.registerActorPath(enemy, enemyPath);
                        CompoundActivity compoundActivityForActor = new MoveThenAttackActivity(Config.Activity.ATTACK_PRIORITY, SimpleAttackActivity.class);
                        compoundActivityForActor.add(new PreCalculatedMovementActivity(enemy, enemyPath));
                        compoundActivityForActor.add(new SimpleAttackActivity(enemy, actor));
                        enemy.getActivityStack().add(compoundActivityForActor);
                    }
                }
                return true;
            } else {
                actor.getActivityStack().add(new SimpleAttackActivity(actor, enemy));
            }
        }

        return false;
    }

    private boolean isActorAlreadyFighting(Actor enemy) {
        return enemy.getActivityStack().contains(SimpleAttackActivity.class) ||
                enemy.getActivityStack().contains(RangedAttackActivity.class) ||
                enemy.getActivityStack().contains(OffensiveSpellCastActivity.class);
    }
}
