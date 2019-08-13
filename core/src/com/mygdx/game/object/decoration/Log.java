package com.mygdx.game.object.decoration;

import com.mygdx.game.logic.Point;
import com.mygdx.game.object.WorldObject;

import java.util.Random;

public class Log implements WorldObject, Decoration, Rotatable {

    private final float worldMapSize = new Random().nextFloat() / 2f + 0.5f;

    private boolean flipX;
    private boolean flipY;

    private Point coordinates;

    public Log(Point point) {
        coordinates = point;
        flipX = new Random().nextInt() % 2 == 0;
        flipY = new Random().nextInt() % 2 == 0;
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

    @Override
    public boolean getFlipX() {
        return flipX;
    }

    @Override
    public boolean getFlipY() {
        return flipY;
    }
}
