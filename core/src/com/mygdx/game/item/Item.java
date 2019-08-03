package com.mygdx.game.item;

import com.mygdx.game.logic.Point;

public interface Item {
    void setCoordinates(Point point);
    int getX();
    int getY();
    String getDescription();
    String getName();
}
