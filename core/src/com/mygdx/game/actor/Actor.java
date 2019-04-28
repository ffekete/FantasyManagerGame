package com.mygdx.game.actor;

import com.mygdx.game.actor.component.Attributes;
import com.mygdx.game.actor.inventory.Inventory;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.item.Equipable;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.weapon.Weapon;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.ActivityStack;

import java.util.List;

public interface Actor {

    int getX();
    int getY();

    ActivityStack getActivityStack();
    Map2D getCurrentMap();
    void setCurrentMap(Map2D map);

    int getMovementSpeed();
    void setCoordinates(int x, int y);
    boolean isHungry();
    void increaseHunger(int amount);
    void decreaseHunger(int amount);
    int getHungerLevel();
    void pickUp(Item item);
    Inventory getInventory();
    float getxOffset();
    float getyOffset();
    void setxOffset(float xOffset);
    void setyOffset(float yOffset);
    Alignment getAlignment();
    void setAlignment(Alignment alignment);
    int getAttackSpeed();
    String getName();
    void setName(String name);

    void equip(Equipable equipable);
    int getHp();
    int getMaxHp();
    void setHp(int value);
    List<Weapon> getWeapons();
    int getAttribute(Attributes attributes);

    // ******************   ActorAnimation  ********************
    Activity getCurrentActivity();

}
