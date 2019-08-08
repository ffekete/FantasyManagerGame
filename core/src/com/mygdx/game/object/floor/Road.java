package com.mygdx.game.object.floor;

import com.mygdx.game.logic.Point;
import com.mygdx.game.object.WorldObject;

public class Road implements TileableFloorObject, WorldObject, Floor {
    private Point coordinates;

    public Road(Point coordinates) {
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
        return 1f;
    }
}
