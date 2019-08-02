package com.mygdx.game.renderer.direction;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.logic.activity.single.*;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.attack.AttackController;

public class DirectionSelector {

    public static final DirectionSelector INSTANCE = new DirectionSelector();

    public Direction getDirection(Actor actor) {
        if(PreCalculatedMovementActivity.class.isAssignableFrom(actor.getCurrentActivity().getCurrentClass())
                || BuildActivity.class.isAssignableFrom(actor.getCurrentActivity().getCurrentClass())
                || MovementActivity.class.isAssignableFrom(actor.getCurrentActivity().getCurrentClass())
                || ExplorationActivity.class.isAssignableFrom(actor.getCurrentActivity().getCurrentClass())) {
            return ActorMovementHandler.INSTANCE.getDirection(actor);
        }

        if(WaitActivity.class.isAssignableFrom(actor.getCurrentActivity().getCurrentClass())) {

            return Direction.UP;
        }

        if(SimpleAttackActivity.class.isAssignableFrom(actor.getCurrentActivity().getCurrentClass()) || RangedAttackActivity.class.isAssignableFrom(actor.getCurrentActivity().getCurrentClass())) {
            Direction direction = AttackController.INSTANCE.getAttackingDirection(actor);
            if(direction != null) {
                return direction;
            }
        }

        // default direction
        return Direction.UP;
    }

    private DirectionSelector() {
    }
}
