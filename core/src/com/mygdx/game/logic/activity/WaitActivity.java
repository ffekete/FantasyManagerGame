package com.mygdx.game.logic.activity;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.pathfinding.PathFinder;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WaitActivity implements Activity {

    private boolean suspended = false;
    private final Actor actor;
    private PathFinder pathFinder;
    private int priorityModifier = 0;
    private boolean firstRun = true;
    private Integer counter = 0;
    private boolean done = false;
    private ActorMovementHandler actorMovementHandler;
    private Future<List<PathFinder.Node>> path;
    private int speed;
    private int range = 0;
    Actor enemy;

    public WaitActivity(Actor actor, Actor enemy, int range, PathFinder pathFinder) {
        this.actor = actor;
        this.pathFinder = pathFinder;
        this.actorMovementHandler = ActorMovementHandler.INSTANCE;
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

    public int getMovementSpeed() {
        return speed;
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
        return 100 + priorityModifier;
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

}
