package com.mygdx.game.logic.activity.single;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.ThreadManager;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.pathfinding.PathFinder;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class PreCalculatedMovementActivity implements Activity {

    private boolean suspended = false;
    private final Actor actor;
    private int priorityModifier = 0;
    private boolean firstRun = true;
    private Integer counter = 0;
    private boolean done = false;
    private ActorMovementHandler actorMovementHandler;
    private int speed;
    private int targetX;
    private int targetY;
    private final List<PathFinder.Node> path;

    public PreCalculatedMovementActivity(Actor actor, List<PathFinder.Node> path) {
        this.actor = actor;
        this.path = path;
        this.actorMovementHandler = ActorMovementHandler.INSTANCE;
        targetX = path.get(0).getX();
        targetY = path.get(0).getY();
    }

    @Override
    public boolean isDone() {
        int a = Math.abs(actor.getX() - targetX);
        int b = Math.abs(actor.getY() - targetY);
        //return (done || (Math.sqrt(a*a + b*b) <= range));
        return done;
    }

    @Override
    public void update() {
        speed = actor.getMovementSpeed();
        if(path.size() > 0) {
            targetX = path.get(0).getX();
            targetY = path.get(0).getY();
        }
        done = (!actorMovementHandler.moveToNextPathPoint(actor));
        actor.setxOffset(0);
        actor.setyOffset(0);
    }

    @Override
    public void init() {
        actorMovementHandler.registerActorPath(actor, path);

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
        actorMovementHandler.updateActorOffsetCoordinates(actor, speed);
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

    @Override
    public Class<? extends Activity> getMainClass() {
        return PreCalculatedMovementActivity.class;
    }

}
