package com.mygdx.game.renderer.direction;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.logic.activity.ExplorationActivity;
import com.mygdx.game.logic.activity.MovementActivity;
import com.mygdx.game.logic.activity.SimpleAttackActivity;
import com.mygdx.game.logic.activity.WaitActivity;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.attack.AttackController;

public class DirectionDecision {

    public static final DirectionDecision INSTANCE = new DirectionDecision();

    public Direction getDirection(Actor actor) {
        if(MovementActivity.class.isAssignableFrom(actor.getCurrentActivity().getCurrentClass())
                || ExplorationActivity.class.isAssignableFrom(actor.getCurrentActivity().getCurrentClass()))
            return ActorMovementHandler.INSTANCE.getDirection(actor);

        if(WaitActivity.class.isAssignableFrom(actor.getCurrentActivity().getCurrentClass())) {
            return Direction.DOWN;
        }

        if(SimpleAttackActivity.class.isAssignableFrom(actor.getCurrentActivity().getCurrentClass())) {
            Direction direction = AttackController.INSTANCE.getAttackingDirection(actor);
            if(direction != null)
                return direction;
        }

        // default direction
        return Direction.DOWN;
    }

    private DirectionDecision() {
    }
}
