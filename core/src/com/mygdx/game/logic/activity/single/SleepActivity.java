package com.mygdx.game.logic.activity.single;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.time.DayTimeCalculator;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.furniture.Bed;
import com.mygdx.game.object.house.House;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.ObjectRegistry;

public class SleepActivity implements Activity {

    private Actor actor;
    private int counter = 0;
    private boolean firstRun = true;
    private boolean suspended = false;

    public SleepActivity(Actor actor) {
        this.actor = actor;
        ActorMovementHandler.INSTANCE.updateDirection(actor, Direction.LEFT);
    }

    @Override
    public boolean isDone() {
        return (actor.getSleepinessLevel() <= Config.Rules.BASE_SLEEPINESS_LEVEL && !DayTimeCalculator.INSTANCE.isItNight());
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
        WorldObject object = getObject(actor.getX(), actor.getY());
        if (object != null && Bed.class.isAssignableFrom(object.getClass())) {


            if (object.getX() + 1 < actor.getCurrentMap().getWidth() && !actor.getCurrentMap().getTile((int) object.getX() + 1, (int) object.getY()).isObstacle() &&
                    !actor.getCurrentMap().getTile((int) object.getX() + 1, (int) object.getY()).isObstacle() &&
                    getObject(actor.getX() + 1, actor.getY()) == null) {
                ActorMovementHandler.INSTANCE.moveTo(actor, Point.of(object.getX() + 1, object.getY()));
                ActorMovementHandler.INSTANCE.getChangedCoordList().add(actor);

            } else if (object.getX() - 1 >= 0 && !actor.getCurrentMap().getTile((int) object.getX() - 1, (int) object.getY()).isObstacle() &&
                    !actor.getCurrentMap().getTile((int) object.getX() - 1, (int) object.getY()).isObstacle() && getObject(actor.getX() - 1, actor.getY()) == null) {
                ActorMovementHandler.INSTANCE.moveTo(actor, Point.of(object.getX() - 1, object.getY()));
                ActorMovementHandler.INSTANCE.getChangedCoordList().add(actor);

            } else if (object.getY() + 1 < actor.getCurrentMap().getHeight() && !actor.getCurrentMap().getTile((int) object.getX(), (int) object.getY() + 1).isObstacle() &&
                    !actor.getCurrentMap().getTile((int) object.getX(), (int) object.getY() + 1).isObstacle() && getObject(actor.getX(), actor.getY() + 1) == null) {
                ActorMovementHandler.INSTANCE.moveTo(actor, Point.of(object.getX(), object.getY() + 1));
                ActorMovementHandler.INSTANCE.getChangedCoordList().add(actor);

            } else if (object.getY() + 1 >= 0 && !actor.getCurrentMap().getTile((int) object.getX(), (int) object.getY() - 1).isObstacle() &&
                    !actor.getCurrentMap().getTile((int) object.getX(), (int) object.getY() - 1).isObstacle() && getObject(actor.getX(), actor.getY() - 1) != null) {
                ActorMovementHandler.INSTANCE.moveTo(actor, Point.of(object.getX(), object.getY() - 1));
                ActorMovementHandler.INSTANCE.getChangedCoordList().add(actor);

            } else if (object.getX() + 1 < actor.getCurrentMap().getWidth() && !actor.getCurrentMap().getTile((int) object.getX() + 1, (int) object.getY()).isObstacle() &&
                    !actor.getCurrentMap().getTile((int) object.getX() + 1, (int) object.getY()).isObstacle()) {
                ActorMovementHandler.INSTANCE.moveTo(actor, Point.of(object.getX() + 1, object.getY()));
                ActorMovementHandler.INSTANCE.getChangedCoordList().add(actor);

            } else if (object.getX() - 1 >= 0 && !actor.getCurrentMap().getTile((int) object.getX() - 1, (int) object.getY()).isObstacle() &&
                    !actor.getCurrentMap().getTile((int) object.getX() - 1, (int) object.getY()).isObstacle()) {
                ActorMovementHandler.INSTANCE.moveTo(actor, Point.of(object.getX() - 1, object.getY()));
                ActorMovementHandler.INSTANCE.getChangedCoordList().add(actor);

            } else if (object.getY() + 1 < actor.getCurrentMap().getHeight() && !actor.getCurrentMap().getTile((int) object.getX(), (int) object.getY() + 1).isObstacle() &&
                    !actor.getCurrentMap().getTile((int) object.getX(), (int) object.getY() + 1).isObstacle()) {
                ActorMovementHandler.INSTANCE.moveTo(actor, Point.of(object.getX(), object.getY() + 1));
                ActorMovementHandler.INSTANCE.getChangedCoordList().add(actor);

            } else if (object.getY() + 1 >= 0 && !actor.getCurrentMap().getTile((int) object.getX(), (int) object.getY() - 1).isObstacle() &&
                    !actor.getCurrentMap().getTile((int) object.getX(), (int) object.getY() - 1).isObstacle()) {
                ActorMovementHandler.INSTANCE.moveTo(actor, Point.of(object.getX(), object.getY() - 1));
                ActorMovementHandler.INSTANCE.getChangedCoordList().add(actor);
            }
        }
    }

    private WorldObject getObject(int x, int y) {
        return ObjectRegistry.INSTANCE.getObjectGrid().get(actor.getCurrentMap())[x][y][1];
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

    @Override
    public Class<? extends Activity> getMainClass() {
        return this.getClass();
    }
}
