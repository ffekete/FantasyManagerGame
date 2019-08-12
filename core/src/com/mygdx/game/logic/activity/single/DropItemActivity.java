package com.mygdx.game.logic.activity.single;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Item;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.CooldownActivity;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.object.StorageArea;
import com.mygdx.game.registry.ItemRegistry;

public class DropItemActivity implements Activity, CooldownActivity {

    private ItemRegistry itemRegistry = ItemRegistry.INSTANCE;

    private boolean firstRun = true;
    private int priority = Config.Activity.PICKUP_PRIORITY;
    private int counter = 0;

    private final Actor actor;
    private final Item item;
    private boolean suspended = false;

    private StorageArea storageArea;

    public DropItemActivity(Actor actor, Item item, StorageArea storageArea) {
        this.actor = actor;
        this.item = item;
        this.storageArea = storageArea;
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
        return Math.abs(actor.getX() - storageArea.getX()) < 2 && Math.abs(actor.getY() - storageArea.getY()) < 2;
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

        actor.getInventory().remove(item);

        if(storageArea.getStoredAmount() > 0) {
            storageArea.setStoredAmount(storageArea.getStoredAmount() + 1);
        } else {
            storageArea.setStoredItem(item.getClass());
            storageArea.setStoredAmount(1);
        }

        item.setCoordinates(storageArea.getCoordinates());

        ItemRegistry.INSTANCE.add(actor.getCurrentMap(), item);

        ActorMovementHandler.INSTANCE.clearPath(actor);

        // todo what to do with this? itemRegistry.getAllItems(actor.getCurrentMap()).remove(item);
    }

    @Override
    public boolean isCancellable() {
        // item is gone
        return !actor.getInventory().has(item);
    }

    @Override
    public Class getCurrentClass() {
        return this.getClass();
    }

    @Override
    public Activity getCurrentActivity() {
        return this;
    }

}
