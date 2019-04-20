package com.mygdx.game.actor;

import com.mygdx.game.creator.map.Map2D;
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
}
