package com.mygdx.game.logic.activity.single;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.activity.Activity;

public class WaitActivity implements Activity {

    private boolean suspended = false;
    private final Actor actor;
    private int priorityModifier = 0;
    private boolean firstRun = true;
    private Integer counter = 0;
    private boolean done = false;
    private int speed;
    private int range = 1;
    Actor enemy;

    public WaitActivity(Actor actor, Actor enemy, int range) {
        this.actor = actor;
        this.range = range;
        this.enemy = enemy;
    }

    @Override
    public boolean isDone() {
        return (done || (Math.abs(actor.getX() - enemy.getX()) <= range && Math.abs(actor.getY() - enemy.getY()) <= range));
    }

    @Override
    public void update() {

    }

    @Override
    public void init() {
        actor.setxOffset(0);
        actor.setyOffset(0);

        firstRun = false;
        speed = actor.getMovementSpeed();
    }

    @Override
    public void cancel() {
    }

    @Override
    public int getPriority() {
        return Config.Activity.WAIT_PRIORITY + priorityModifier;
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
        init();
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

    public void setPriorityModifier(int priorityModifier) {
        this.priorityModifier = priorityModifier;
    }

    @Override
    public int compareTo(Activity o) {
        return Integer.compare(getPriority(), o.getPriority());
    }

    @Override
    public void countDown() {
        counter = (counter + 1) % speed;
    }

    @Override
    public boolean isTriggered() {
        return counter == speed - 1;
    }

    @Override
    public Class getCurrentClass() {
        return this.getClass();
    }

    @Override
    public Activity getCurrentActivity() {
        return this;
    }

    @Override
    public Class<? extends Activity> getMainClass() {
        return this.getClass();
    }
}
