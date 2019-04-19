package com.mygdx.game.actor;

import com.mygdx.game.logic.activity.ActivityStack;

public interface Actor {

    int getX();
    int getY();

    ActivityStack getActivityStack();
    int getMovementSpeed();
}
