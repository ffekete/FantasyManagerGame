package com.mygdx.game.logic.activity.single;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.ThreadManager;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.pathfinding.PathFinder;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

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
    private Future<List<PathFinder.Node>> path;
    private int speed;
    private int range = 0;
    private ExecutorService executor = ThreadManager.INSTANCE.getExecutor();

    public MovementActivity(Actor actor, int targetX, int targetY, int range, PathFinder pathFinder) {
        this.actor = actor;
        this.targetX = targetX;
        this.targetY = targetY;
        this.pathFinder = pathFinder;
        this.actorMovementHandler = ActorMovementHandler.INSTANCE;
        this.range = range;
    }

    @Override
    public boolean isDone() {
        int a = Math.abs(actor.getX() - targetX);
        int b = Math.abs(actor.getY() - targetY);
        return (done || (Math.sqrt(a * a + b * b) <= range));
    }

    @Override
    public void update() {
        if (path != null && path.isDone()) {
            try {
                actorMovementHandler.registerActorPath(actor, path.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            path = null;
        } else if (path != null && !path.isDone()) {
            //wait
            return;
        }

        speed = actor.getMovementSpeed();
        done = (!actorMovementHandler.moveToNextPathPoint(actor));
        actor.setxOffset(0);
        actor.setyOffset(0);
    }

    @Override
    public void init() {
        pathFinder.init(actor.getCurrentMap());
        path = executor.submit(() -> pathFinder.findAStar(new Point(actor.getX(), actor.getY()), new Point(targetX, targetY)));

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
        return Config.Activity.MOVEMENT_PRIORITY + priorityModifier;
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
        actorMovementHandler.clearPath(actor);
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
        counter = (counter + 1) % (speed);
        if (actorMovementHandler.hasPath(actor)) {
            actorMovementHandler.updateActorOffsetCoordinates(actor, speed);
        }
    }

    @Override
    public boolean isTriggered() {
        return counter == speed - 1;
    }

    @Override
    public Class getCurrentClass() {
        return this.getClass();
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    @Override
    public Activity getCurrentActivity() {
        return this;
    }


}
