package com.mygdx.game.logic.activity;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Item;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.registry.ItemRegistry;

public class PickUpItemActivity implements Activity, CooldownActivity {

    private ItemRegistry itemRegistry = ItemRegistry.INSTANCE;

    private boolean firstRun = true;
    private int priority = Config.Activity.PICKUP_PRIORITY;
    private int counter = 0;

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
        return Math.abs(actor.getX() - item.getX()) < 2 && Math.abs(actor.getY() - item.getY()) < 2;
    }

    @Override
    public void update() {

    }

    @Override
    public void init() {
        firstRun = false;
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
        actor.setxOffset(0);
        actor.setyOffset(0);
        System.out.println(" I picked up " + item);
        actor.pickUp(item);
        ActorMovementHandler.INSTANCE.clearPath(actor);
        itemRegistry.getAllItems(actor.getCurrentMap()).remove(item);
    }

    @Override
    public boolean isCancellable() {
        // food is gone
        return !itemRegistry.getAllItems(actor.getCurrentMap()).contains(item);
    }

    @Override
    public Class getCurrentClass() {
        return this.getClass();
    }
}
