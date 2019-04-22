package com.mygdx.game.logic.activity;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Food;
import com.mygdx.game.item.Item;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.registry.ItemRegistry;

public class PickUpItemActivity implements Activity, CooldownActivity {

    private ItemRegistry itemRegistry = ItemRegistry.INSTANCE;

    private PathFinder pathFinder = new PathFinder();
    private boolean firstRun = true;
    private int priority = 98;
    private int counter = 0;
    private MovementActivity movementActivity;

    private final Actor actor;
    private final Item item;
    private boolean suspended = false;

    public PickUpItemActivity(Actor actor, Item item) {
        this.actor = actor;
        this.item = item;
    }

    @Override
    public void countDown() {
        counter = (counter + 1) % actor.getMovementSpeed();
    }

    @Override
    public boolean isTriggered() {
        return counter == actor.getMovementSpeed() -1;
    }

    @Override
    public boolean isDone() {
        return actor.getX() == item.getX() && actor.getY() == item.getY();
    }

    @Override
    public void update() {
        movementActivity.update();
    }

    @Override
    public void init() {
        firstRun = false;
        this.movementActivity = new MovementActivity(actor, item.getX(), item.getY(), pathFinder);
        movementActivity.init();
    }

    @Override
    public void cancel() {
        System.out.println(actor + " cancelled.");
    }

    @Override
    public int getPriority() {
        return priority;
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
        if(itemRegistry.getAllItems(actor.getCurrentMap()).contains(item)) {
            movementActivity.init();
        }
    }

    @Override
    public int compareTo(Activity o) {
        return Integer.compare(priority, o.getPriority());
    }

    @Override
    public boolean isSuspended() {
        return suspended;
    }

    @Override
    public void clear() {
        System.out.println(" I picked up " + item);
        actor.pickUp(item);
        itemRegistry.getAllItems(actor.getCurrentMap()).remove(item);
    }

    @Override
    public boolean isCancellable() {
        // food is gone
        return !itemRegistry.getAllItems(actor.getCurrentMap()).contains(item);
    }
}
