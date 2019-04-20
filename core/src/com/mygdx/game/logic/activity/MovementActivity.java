package com.mygdx.game.logic.activity;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.pathfinding.PathFinder;

import java.util.List;

public class MovementActivity implements Activity {

    private boolean suspended = false;
    private final Actor actor;
    private int targetX;
    private int targetY;
    private PathFinder pathFinder;
    private int priorityModifier = 0;
    private boolean firstRun = true;
    private Integer counter = 0;
    private boolean done = false;
    private ActorMovementHandler actorMovementHandler;

    public MovementActivity(Actor actor, int targetX, int targetY, PathFinder pathFinder) {
        this.actor = actor;
        this.targetX = targetX;
        this.targetY = targetY;
        this.pathFinder = pathFinder;
        this.actorMovementHandler = new ActorMovementHandler();
    }

    @Override
    public boolean isDone() {
        return (done || (actor.getX() == targetX && actor.getY() == targetY));
    }

    @Override
    public void update() {
        //actor.moveToNextPathPoint();
        done = (!actorMovementHandler.moveToNextPathPoint(actor));
    }

    @Override
    public void init() {
        System.out.println("Starting activity");
        pathFinder.init(actor.getCurrentMap());
        List<PathFinder.Node> path = pathFinder.findAStar(new Point(actor.getX(), actor.getY()), new Point(targetX, targetY));
        actorMovementHandler.registerActorPath(actor, path);
        firstRun = false;
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
        counter = (counter + 1) % actor.getMovementSpeed();
        if(counter < 0)
            counter = 0;
    }

    @Override
    public boolean isTriggered() {
        return counter == actor.getMovementSpeed() -1;
    }

}
