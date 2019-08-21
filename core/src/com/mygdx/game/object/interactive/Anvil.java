package com.mygdx.game.object.interactive;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.worker.Smith;
import com.mygdx.game.item.Craftable;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.ItemFactory;
import com.mygdx.game.item.category.Category;
import com.mygdx.game.item.category.Tier1;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.*;
import com.mygdx.game.object.furniture.Furniture;

public class Anvil implements WorldObject, Obstacle, CraftingObject {

    private final float worldMapSize = 1f;

    private Point coordinates;

    private Actor user;

    private Craftable itemToCreate;

    private Float progress = 0f;

    public Anvil(Point point) {
        coordinates = point;
    }

    @Override
    public float getX() {
        return coordinates.getX();
    }

    @Override
    public float getY() {
        return coordinates.getY();
    }

    @Override
    public void setCoordinates(Point point) {
        this.coordinates = point;
    }

    @Override
    public Point getCoordinates() {
        return this.coordinates;
    }

    @Override
    public float getWorldMapSize() {
        return worldMapSize;
    }

    @Override
    public void onInteract(Actor actor) {
        this.user = actor;
        progress = 0f;
    }

    @Override
    public boolean canInteract(Actor actor) {
        return Smith.class.isAssignableFrom(actor.getClass());
    }

    @Override
    public void finished(Actor actor) {
        user = null;
        progress = 0f;
    }

    @Override
    public boolean isFinished() {
        return progress >= 100f;
    }

    @Override
    public void start(Class<? extends Craftable> craftable) {
        try {
            this.itemToCreate = craftable.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        this.progress = 0f;
    }

    @Override
    public Float getProgress() {
        return progress;
    }

    @Override
    public void addProgress(float amount) {
        progress += amount;
    }

    @Override
    public Craftable finish() {
        user.getInventory().add(itemToCreate);
        user = null;
        return itemToCreate;
    }

    @Override
    public Class<? extends Category> getMaxTier() {
        return Tier1.class;
    }

    @Override
    public void cancel() {
        user = null;
        itemToCreate = null;
    }

    @Override
    public Actor getUser() {
        return user;
    }

    @Override
    public Craftable getItem() {
        return itemToCreate;
    }
}
