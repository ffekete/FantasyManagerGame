package com.mygdx.game.logic.activity.single;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.common.util.MathUtil;
import com.mygdx.game.logic.pathfinding.PathFinder;

public class SupportActivity extends MovementActivity {

    private final Actor targetActor;
    private final Actor actor;


    public SupportActivity(Actor actor, Actor targetActor, int range) {
        super(actor, targetActor.getX(), targetActor.getY(), range);
        this.targetActor = targetActor;
        this.actor = actor;
    }

    @Override
    public void resume() {
        super.setTargetX(targetActor.getX());
        super.setTargetY(targetActor.getY());
        super.resume();
    }

    @Override
    public int getPriority() {
        return Config.Activity.SUPPORT_PRIORITY;
    }

    @Override
    public boolean isCancellable() {
        return super.isCancellable()
                || !actor.getCurrentMap().equals(targetActor.getCurrentMap())
                || MathUtil.distance(actor.getCoordinates(), targetActor.getCoordinates()) > Config.Rules.FOLLOW_DISTANCE;
    }
}
