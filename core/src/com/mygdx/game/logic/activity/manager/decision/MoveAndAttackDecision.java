package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.common.SelectionUtils;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.CompoundActivity;
import com.mygdx.game.logic.activity.compound.MoveThenAttackActivity;
import com.mygdx.game.logic.activity.single.PreCalculatedMovementActivity;
import com.mygdx.game.logic.activity.single.SimpleAttackActivity;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.MapRegistry;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.common.util.MathUtil.distance;

public class MoveAndAttackDecision implements Decision {

    private final ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    private final ActorMovementHandler actorMovementHandler = ActorMovementHandler.INSTANCE;
    private final MapRegistry mapRegistry = MapRegistry.INSTANCE;

    @Override
    public boolean decide(Actor actor) {

        // already attacking, the decision chain should end here
        if(actor.getActivityStack().contains(MoveThenAttackActivity.class) ||
                actor.getActivityStack().contains(SimpleAttackActivity.class)) {
            return true;
        }

        if (!actor.getActivityStack().contains(MoveThenAttackActivity.class) &&
                !actor.getActivityStack().contains(SimpleAttackActivity.class)) {

            Actor enemy = SelectionUtils.findClosestEnemy(actor, actorRegistry.getActors(actor.getCurrentMap()), Config.ATTACK_DISTANCE);
            if (enemy != null) {
                if (distance(actor.getCoordinates(), enemy.getCoordinates()) > actor.getAttackRange()) {

                    actor.getActivityStack().clear();

                    PathFinder pathFinder = mapRegistry.getPathFinderFor(actor.getCurrentMap());

                    long startDate = System.currentTimeMillis();
                    List<PathFinder.Node> path;
                    if (PreCalculatedMovementActivity.class.isAssignableFrom(enemy.getActivityStack().getCurrent().getCurrentClass())) {

                        PreCalculatedMovementActivity movementActivity = ((PreCalculatedMovementActivity) enemy.getActivityStack().getCurrent().getCurrentActivity());
                        Point target;
                        if(movementActivity == null) {
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
                        actor.getActivityStack().add(new SimpleAttackActivity(actor, enemy));
                    } else {
                        actorMovementHandler.clearPath(actor);
                        List<PathFinder.Node> actorPath = new ArrayList<>();
                        int start = halfWay;
                        int end = path.size();

                        // if enemy is already fighting
                        if (SimpleAttackActivity.class.isAssignableFrom(enemy.getActivityStack().getCurrent().getCurrentClass())) {
                            start = 1;
                            end = path.size();
                        } else if (PreCalculatedMovementActivity.class.isAssignableFrom(enemy.getActivityStack().getCurrent().getCurrentClass())) {
                            start = 1;
                            end = path.size();
                        }
                        for (int i = start; i < end; i++) {
                            actorPath.add(path.get(i));
                        }
                        //actorMovementHandler.registerActorPath(actor, actorPath);
                        CompoundActivity compoundActivityForActor = new MoveThenAttackActivity(Config.Activity.MOVE_THEN_ATTACK_PRIORITY);
                        compoundActivityForActor.add(new PreCalculatedMovementActivity(actor, actorPath));
                        compoundActivityForActor.add(new SimpleAttackActivity(actor, enemy));
                        actor.getActivityStack().add(compoundActivityForActor);

                    }

                    // todo what to do if the actor kills an enemy when this enemy is on its way towards the actor?

                    // if enemy is not already fighting then give a path to this enemy as well to the actor
                    if (!enemy.getActivityStack().contains(SimpleAttackActivity.class) && !enemy.getActivityStack().contains(MoveThenAttackActivity.class)) {
                        enemy.getActivityStack().clear();
                        if (path.size() < enemy.getAttackRange()) {
                            enemy.getActivityStack().add(new SimpleAttackActivity(enemy, actor));
                        } else {
                            actorMovementHandler.clearPath(enemy);
                            List<PathFinder.Node> enemyPath = new ArrayList<>();
                            for (int i = halfWay - 1; i >= 0; i--) {
                                enemyPath.add(path.get(i));
                            }
                            //actorMovementHandler.registerActorPath(enemy, enemyPath);
                            CompoundActivity compoundActivityForActor = new MoveThenAttackActivity(Config.Activity.MOVE_THEN_ATTACK_PRIORITY);
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
        }
        return false;
    }
}
