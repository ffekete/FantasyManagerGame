package com.mygdx.game.object.decoration;

import com.mygdx.game.logic.Point;
import com.mygdx.game.object.WorldObject;

import java.util.Random;

public class BlueFlower implements WorldObject, Decoration, Growable {

    private float worldMapSize = new Random().nextFloat() / 2f + 0.3f;

    private Point coordinates;

    public BlueFlower(Point point) {
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
        return worldMapSize > getMaxSize() ? getMaxSize() : worldMapSize;
    }

    @Override
    public void grow() {
        if(worldMapSize < getMaxSize())
            worldMapSize += 0.001f;
    }

    @Override
    public float getMaxSize() {
        return 0.8f;
    }
}
