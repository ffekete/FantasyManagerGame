package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.logic.activity.CompoundActivity;
import com.mygdx.game.logic.activity.compound.MoveThenAttackActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.activity.single.SimpleAttackActivity;
import com.mygdx.game.logic.activity.single.WaitActivity;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.registry.ActorRegistry;

public class MoveAndAttackDecision implements Decision {

    private final ActorRegistry actorRegistry = ActorRegistry.INSTANCE;

    @Override
    public boolean decide(Actor actor) {
        if(!actor.getActivityStack().contains(MoveThenAttackActivity.class)) {
            Actor enemy = DecisionUtils.findClosestEnemy(actor, actorRegistry.getActors(actor.getCurrentMap()), Config.ATTACK_DISTANCE);
            if(enemy != null) {
                CompoundActivity compoundActivity = new MoveThenAttackActivity(Config.Activity.MOVE_THEN_ATTACK_PRIORITY);
                if(actor.getAlignment().equals(Alignment.FRIENDLY)) {
                    compoundActivity.add(new MovementActivity(actor, enemy.getX(), enemy.getY(), 1, new PathFinder()));
                    compoundActivity.add(new SimpleAttackActivity(actor, enemy));
                }
                else {
                    compoundActivity.add(new WaitActivity(actor, enemy, 1));
                    compoundActivity.add(new SimpleAttackActivity(actor, enemy));
                }

                actor.getActivityStack().add(compoundActivity);
                return true;
            }
        }
        return false;
    }
}
