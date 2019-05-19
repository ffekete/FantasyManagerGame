package com.mygdx.game.object;

import com.mygdx.game.logic.Point;

public interface WorldObject {
    float getX();
    float getY();

    void setCoordinates(Point point);
    Point getCoordinates();
}
