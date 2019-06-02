package com.mygdx.game.object.decoration;

import com.mygdx.game.logic.Point;
import com.mygdx.game.object.AnimatedObject;
import com.mygdx.game.object.WorldObject;

import java.util.Random;

public class Tree implements WorldObject {

    private final float worldMapSize = new Random().nextFloat() / 2f + 1.5f;

    private Point coordinates;

    public Tree(Point point) {
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
