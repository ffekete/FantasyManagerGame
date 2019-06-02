package com.mygdx.game.object.decoration;

import com.mygdx.game.logic.Point;
import com.mygdx.game.object.AnimatedObject;
import com.mygdx.game.object.WorldObject;

public class SpiderWeb implements WorldObject, AnimatedObject {

    private Point coordinates;

    public SpiderWeb(Point point) {
        coordinates = point;
    }

    @Override
    public float getX() {
        return coordinates.getX();
    }

    @Override
    public float getY() {
        return coordinates.getY();
    }

    @Override
    public void setCoordinates(Point point) {
        this.coordinates = point;
    }

    @Override
    public Point getCoordinates() {
        return this.coordinates;
    }

    @Override
    public float getWorldMapSize() {
        return 1f;
    }
}
