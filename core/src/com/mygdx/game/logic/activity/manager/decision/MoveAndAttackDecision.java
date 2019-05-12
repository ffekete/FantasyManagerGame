package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
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
        if(!actor.getActivityStack().contains(MoveThenAttackActivity.class) &&
            !actor.getActivityStack().contains(SimpleAttackActivity.class)) {

            Actor enemy = DecisionUtils.findClosestEnemy(actor, actorRegistry.getActors(actor.getCurrentMap()), Config.ATTACK_DISTANCE);
            if(enemy != null) {

                if(distance(actor.getCoordinates(), enemy.getCoordinates()) > actor.getAttackRange()) {

                    PathFinder pathFinder = mapRegistry.getPathFinderFor(actor.getCurrentMap());
                    List<PathFinder.Node> path = pathFinder.findAStar(actor.getCoordinates(), enemy.getCoordinates());

                    int halfWay = path.size() / 2;

                    if(path.size() <= actor.getAttackRange()) {
                        actor.getActivityStack().add(new SimpleAttackActivity(actor, enemy));
                    } else {
                        actorMovementHandler.clearPath(actor);
                        List<PathFinder.Node> actorPath = new ArrayList<>();
                        int start = halfWay;
                        int end = path.size();

                        // if enemy is already fighting
                        if(SimpleAttackActivity.class.isAssignableFrom(enemy.getActivityStack().getCurrent().getCurrentClass())) {
                            start = 0;
                            end = path.size();
                        }
                        for(int i = start; i < end; i++) {
                            actorPath.add(path.get(i));
                        }
                        //actorMovementHandler.registerActorPath(actor, actorPath);
                        CompoundActivity compoundActivityForActor = new MoveThenAttackActivity(Config.Activity.MOVE_THEN_ATTACK_PRIORITY);
                        compoundActivityForActor.add(new PreCalculatedMovementActivity(actor, actor.getAttackRange(), actorPath));
                        compoundActivityForActor.add(new SimpleAttackActivity(actor, enemy));
                        actor.getActivityStack().add(compoundActivityForActor);

                    }

                    // todo what to do if the actor kills an enemy when this enemy is on its way towards the actor?

                    // if enemy is not already fighting then give a path to this enemy as well to the actor
                    if(!SimpleAttackActivity.class.isAssignableFrom(enemy.getActivityStack().getCurrent().getCurrentClass())) {
                        if (path.size() <= enemy.getAttackRange()) {
                            enemy.getActivityStack().add(new SimpleAttackActivity(enemy, actor));
                        } else {
                            actorMovementHandler.clearPath(enemy);
                            List<PathFinder.Node> enemyPath = new ArrayList<>();
                            for (int i = halfWay + actor.getAttackRange(); i >= 0; i--) {
                                enemyPath.add(path.get(i));
                            }
                            //actorMovementHandler.registerActorPath(enemy, enemyPath);
                            CompoundActivity compoundActivityForActor = new MoveThenAttackActivity(Config.Activity.MOVE_THEN_ATTACK_PRIORITY);
                            compoundActivityForActor.add(new PreCalculatedMovementActivity(enemy, enemy.getAttackRange(), enemyPath));
                            compoundActivityForActor.add(new SimpleAttackActivity(enemy, actor));
                            enemy.getActivityStack().add(compoundActivityForActor);
                        }
                    }

                    return true;




//                    CompoundActivity compoundActivityForActor = new MoveThenAttackActivity(Config.Activity.MOVE_THEN_ATTACK_PRIORITY);
//                    compoundActivityForActor.add(new MovementActivity(actor, enemy.getX(), enemy.getY(), 1, new PathFinder()));
//                    compoundActivityForActor.add(new SimpleAttackActivity(actor, enemy));
//                    actor.getActivityStack().add(compoundActivityForActor);
                }

//
//                if(actor.getAlignment().equals(Alignment.FRIENDLY)) {
//                    compoundActivity.add(new MovementActivity(actor, enemy.getX(), enemy.getY(), 1, new PathFinder()));
//                    compoundActivity.add(new SimpleAttackActivity(actor, enemy));
//                }
//                else {
//                    compoundActivity.add(new WaitActivity(actor, enemy, 1));
//                    compoundActivity.add(new SimpleAttackActivity(actor, enemy));
//                }
            }
        }
        return false;
    }
}
