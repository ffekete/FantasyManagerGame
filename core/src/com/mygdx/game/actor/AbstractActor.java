package com.mygdx.game.actor;

import com.mygdx.game.Config;
import com.mygdx.game.actor.component.Attributes;
import com.mygdx.game.actor.inventory.Inventory;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.item.Equipable;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.weapon.Weapon;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.ActivityStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public abstract class AbstractActor implements Actor {

    private String name;
    private Inventory inventory;
    private Map<Attributes, Integer> baseAttributes;

    private int x;
    private int y;
    private int hungerLevel;
    private Alignment alignment;

    private float xOffset = 0;
    private float yOffset = 0;

    private int actualHp = 20;
    private int hpModifier = 3;

    private Equipable leftHand = null;
    private Equipable rightHand = null;

    private ActivityStack activityStack = new ActivityStack(new PriorityQueue<>(), this);

    private Map2D currentMap;

    public AbstractActor() {
        this.hungerLevel = Config.BASE_HUNGER_LEVEL;
        this.baseAttributes = new HashMap<>();
        for (Attributes a : Attributes.values()) {
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

    @Override
    public int getAttribute(Attributes a) {
        return baseAttributes.get(a);
    }

    public int getMovementSpeed() {
        return 30 - getAttribute(Attributes.Dexterity);
    }

    @Override
    public Map2D getCurrentMap() {
        return currentMap;
    }

    @Override
    public void setCurrentMap(Map2D map) {
        this.currentMap = map;
    }

    @Override
    public float getxOffset() {
        return xOffset;
    }

    @Override
    public float getyOffset() {
        return yOffset;
    }

    @Override
    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    @Override
    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }

    @Override
    public Alignment getAlignment() {
        return this.alignment;
    }

    @Override
    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    @Override
    public int getAttackSpeed() {
        return 30;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void equip(Equipable equipable) {
        if (leftHand == null) {
            leftHand = equipable;
            inventory.remove(equipable);
            //equipable.onEquip(actor);
            System.out.println(name + " equiped in left hand " + equipable);
        } else if (rightHand == null) {
            rightHand = equipable;
            inventory.remove(equipable);
            System.out.println(name + " equiped in right hand " + equipable);
        }
    }

    @Override
    public Activity getCurrentActivity() {
        return activityStack.getCurrent();
    }

    @Override
    public int getHp() {
        return actualHp;
    }

    @Override
    public int getMaxHp() {
        return (getAttribute(Attributes.Endurance) + getAttribute(Attributes.Strength) / 2 + hpModifier);
    }

    @Override
    public void setHp(int value) {
        actualHp = value;
    }

    @Override
    public List<Weapon> getWeapons() {
        List<Weapon> result = new ArrayList<>();
        if(leftHand != null && Weapon.class.isAssignableFrom(leftHand.getClass()))
            result.add(((Weapon)leftHand));
        if(rightHand != null && Weapon.class.isAssignableFrom(rightHand.getClass()))
            result.add(((Weapon)rightHand));
        return result;
    }
}
