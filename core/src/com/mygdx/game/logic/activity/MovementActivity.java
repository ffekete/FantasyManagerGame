package com.mygdx.game.logic.activity;

import com.mygdx.game.actor.MovableActor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.pathfinding.PathFinder;

public class MovementActivity implements Activity {

    private final MovableActor actor;
    private int targetX;
    private int targetY;
    private PathFinder pathFinder;
    private int priorityModifier = 0;
    private boolean firstRun = true;

    public MovementActivity(MovableActor actor, int targetX, int targetY, PathFinder pathFinder) {
        this.actor = actor;
        this.targetX = targetX;
        this.targetY = targetY;
        this.pathFinder = pathFinder;
    }

    @Override
    public boolean isDone() {
        return (actor.getX() == targetX && actor.getY() == targetY) || actor.hasPathPointsLeft();
    }

    @Override
    public void update() {
        actor.moveToNextPathPoint();
    }

    @Override
    public void init() {
        System.out.println("Starting activity");
        actor.setPath(pathFinder.findAStar(new Point(actor.getX(), actor.getY()), new Point(targetX, targetY)));
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

    public void setPriorityModifier(int priorityModifier) {
        this.priorityModifier = priorityModifier;
    }

    @Override
    public int compareTo(Activity o) {
        return Integer.compare(getPriority(), o.getPriority());
    }
}
