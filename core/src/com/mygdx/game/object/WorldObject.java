package com.mygdx.game.object;

import com.mygdx.game.logic.Point;

public interface WorldObject {
    float getX();
    float getY();
    float worldMapSize = 1.0f;

    void setCoordinates(Point point);
    Point getCoordinates();
    float getWorldMapSize();
}
