package com.mygdx.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.inventory.Inventory;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.item.Item;
import com.mygdx.game.logic.activity.ActivityStack;

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
}
