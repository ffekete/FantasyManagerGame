package com.mygdx.game.object.decoration;

import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.AnimatedObject;
import com.mygdx.game.object.WorldObject;

import java.util.Random;


public class GrassV2 implements WorldObject, AnimatedObject, Decoration {

    private Point coordinates;
    private int phase = new Random().nextInt(3);

    public GrassV2(Point coordinates, Map2D map) {
        this.coordinates = coordinates;
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
        return coordinates;
    }

    @Override
    public float getWorldMapSize() {
        return 1F;
    }

    @Override
    public int getPhase() {
        return phase;
    }

    @Override
    public float getSpeed() {
        return 0.015f;
    }
}
