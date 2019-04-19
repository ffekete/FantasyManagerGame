package com.mygdx.game.actor;

public interface MovableActor extends Actor {
    int getMovementSpeed();
    void setCoordinates(int x, int y);
}
