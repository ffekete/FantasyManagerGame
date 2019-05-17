package com.mygdx.game.object;

import com.mygdx.game.logic.Point;

public interface WorldObject {
    int getX();
    int getY();

    void setCoordinates(Point point);
    Point getCoordinates();
}
