package com.mygdx.game.object.decoration;

import com.mygdx.game.logic.Point;
import com.mygdx.game.object.Obstacle;
import com.mygdx.game.object.WorldObject;

import java.util.Random;

public class Rock implements WorldObject, Decoration, Rotatable, Obstacle {

    private Point coordinates;
    private boolean flipX;
    private boolean flipY;
    private float size = new Random().nextFloat() / 2f + 0.5f;

    public Rock(Point coordinates) {
        this.coordinates = coordinates;
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
        return size;
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
