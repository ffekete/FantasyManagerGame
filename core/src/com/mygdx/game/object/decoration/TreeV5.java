package com.mygdx.game.object.decoration;

import com.mygdx.game.logic.Point;
import com.mygdx.game.object.Obstacle;
import com.mygdx.game.object.WorldObject;

import java.util.Random;

public class TreeV5 implements WorldObject, Decoration {

    private final float worldMapSize = new Random().nextFloat() / 2f + 1.1f;

    private Point coordinates;

    public TreeV5(Point point) {
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
        return worldMapSize;
    }
}
