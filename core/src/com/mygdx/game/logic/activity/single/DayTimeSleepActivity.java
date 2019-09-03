package com.mygdx.game.logic.activity.single;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.time.DayTimeCalculator;

public class DayTimeSleepActivity implements Activity {

    private Actor actor;
    private int counter = 0;
    private boolean firstRun = true;
    private boolean suspended = false;

    public DayTimeSleepActivity(Actor actor) {
        this.actor = actor;
        ActorMovementHandler.INSTANCE.updateDirection(actor, Direction.LEFT);
    }

    @Override
    public boolean isDone() {
        return (actor.getSleepinessLevel() <= Config.Rules.BASE_SLEEPINESS_LEVEL && DayTimeCalculator.INSTANCE.isItNight());
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
        suspended = true;
    }

    @Override
    public void resume() {
        suspended = false;
    }

    @Override
    public boolean isSuspended() {
        return suspended;
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
        return DayTimeSleepActivity.class;
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

    @Override
    public Class<? extends Activity> getMainClass() {
        return SleepActivity.class;
    }
}
