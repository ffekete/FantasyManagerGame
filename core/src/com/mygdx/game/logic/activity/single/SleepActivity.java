package com.mygdx.game.logic.activity.single;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.actor.ActorMovementHandler;

public class SleepActivity implements Activity {

    private Actor actor;
    private int counter = 0;
    private boolean firstRun = true;

    public SleepActivity(Actor actor) {
        this.actor = actor;
        ActorMovementHandler.INSTANCE.updateDirection(actor, Direction.LEFT);
    }

    @Override
    public boolean isDone() {
        return actor.getSleepinessLevel() <= Config.Rules.BASE_SLEEPINESS_LEVEL;
    }

    @Override
    public void update() {
        actor.decreaseSleepiness(1000);
    }

    @Override
    public void init() {
        actor.unequip(actor.getLeftHandItem());
        actor.unequip(actor.getRightHandItem());
    }

    @Override
    public void cancel() {
    }

    @Override
    public int getPriority() {
        return Config.CommonActivity.SLEEP_PRIORITY;
    }

    @Override
    public boolean isFirstRun() {
        return firstRun;
    }

    @Override
    public void suspend() {

    }

    @Override
    public void resume() {

    }

    @Override
    public boolean isSuspended() {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean isCancellable() {
        return false;
    }

    @Override
    public Class getCurrentClass() {
        return SleepActivity.class;
    }

    @Override
    public Activity getCurrentActivity() {
        return this;
    }

    @Override
    public void countDown() {
        counter = (counter + 1) % 20;
    }

    @Override
    public boolean isTriggered() {
        return counter == 19;
    }

    @Override
    public int compareTo(Activity o) {
        return Integer.compare(getPriority(), o.getPriority());
    }
}
