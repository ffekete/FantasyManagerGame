package com.mygdx.game.logic.activity.single;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.ThreadManager;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.SoundRegistry;
import com.mygdx.game.sound.DistanceBasedSoundPlayer;
import com.mygdx.game.sound.DistanceBasedSoundPlayerProvider;

import java.util.List;
import java.util.Random;
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
    private int limit = 0;
    private int range = 0;
    private ExecutorService executor = ThreadManager.INSTANCE.getExecutor();
    DistanceBasedSoundPlayer distanceBasedSoundPlayer = DistanceBasedSoundPlayerProvider.INSTANCE.provide();

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

                if (path.get() == null) {
                    System.out.println("Inaccessible area");
                } else {
                    actorMovementHandler.registerActorPath(actor, path.get());
                }
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
        actorMovementHandler.clearPath(actor);
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
            playFootstepSound();
            actorMovementHandler.updateActorOffsetCoordinates(actor, speed);
        }
    }

    private void playFootstepSound() {
        if (MapRegistry.INSTANCE.getCurrentMapToShow().equals(actor.getCurrentMap())) {

            if (counter % 15 == limit && SoundRegistry.INSTANCE.getFor(MovementActivity.class).isPresent()) {
                limit = new Random().nextInt(8) < 4 ? 8 : 0;
                distanceBasedSoundPlayer.play(SoundRegistry.INSTANCE.getFor(MovementActivity.class).get(), actor.getCoordinates(), 0.05f, 0.01f);
            }
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

    public int getCounter() {
        return counter;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public Class<? extends Activity> getMainClass() {
        return this.getClass();
    }

}
