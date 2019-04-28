package com.mygdx.game.logic.activity;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.pathfinding.PathFinder;

import java.util.List;
import java.util.concurrent.Future;

public class IdleActivity implements Activity {

    private boolean suspended = false;
    private final Actor actor;
    private boolean firstRun = true;
    private Integer counter = 0;
    private int speed;

    public IdleActivity(Actor actor) {
        this.actor = actor;
    }

    @Override
    public boolean isDone() {
        // ths activity never stops
        return false;
    }

    @Override
    public void update() {

    }

    @Override
    public void init() {
        System.out.println("Starting activity");

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
        return 120;
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

    @Override
    public Class getCurrentClass() {
        return this.getClass();
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

}
