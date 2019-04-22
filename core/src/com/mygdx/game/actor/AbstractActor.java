package com.mygdx.game.actor;

import com.mygdx.game.Config;
import com.mygdx.game.actor.component.Attributes;
import com.mygdx.game.actor.inventory.Inventory;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.item.Item;
import com.mygdx.game.logic.activity.ActivityStack;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class AbstractActor implements Actor {

    private Inventory inventory;
    private Map<Attributes, Integer> baseAttributes;

    private int x;
    private int y;
    private int hungerLevel;

    private float xOffset = 0;
    private float yOffset = 0;

    private ActivityStack activityStack = new ActivityStack(new PriorityQueue<>());

    private Map2D currentMap;

    public AbstractActor() {
        this.hungerLevel = Config.BASE_HUNGER_LEVEL;
        this.baseAttributes = new HashMap<>();
        for(Attributes a : Attributes.values()) {
            baseAttributes.put(a, 20);
        }
        this.inventory = new Inventory();
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public ActivityStack getActivityStack() {
        return this.activityStack;
    }

    @Override
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // override this later for monsters!
    @Override
    public boolean isHungry() {
        return hungerLevel == Config.BASE_HUNGER_LIMIT;
    }

    @Override
    public void increaseHunger(int amount) {
        hungerLevel = Math.min(Config.BASE_HUNGER_LIMIT, hungerLevel + amount);
    }

    @Override
    public void decreaseHunger(int amount) {
        hungerLevel = Math.max(0, hungerLevel - amount);
    }

    @Override
    public int getHungerLevel() {
        return hungerLevel;
    }

    @Override
    public void pickUp(Item item) {
        this.inventory.add(item);
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public Integer getattribute(Attributes a) {
        return baseAttributes.get(a);
    }

    public int getMovementSpeed() {
        return 30 - getattribute(Attributes.Dexterity);
    }

    @Override
    public Map2D getCurrentMap() {
        return currentMap;
    }

    @Override
    public void setCurrentMap(Map2D map) {
        this.currentMap = map;
    }

    public float getxOffset() {
        return xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }
}
