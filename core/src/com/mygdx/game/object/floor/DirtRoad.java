package com.mygdx.game.object.floor;

import com.mygdx.game.logic.Point;
import com.mygdx.game.object.TileableObject;
import com.mygdx.game.object.WorldObject;

import java.util.Arrays;
import java.util.List;

public class DirtRoad implements TileableFloorObject, WorldObject, Floor, Road {
    private Point coordinates;

    public DirtRoad(Point coordinates) {
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

    @Override
    public List<Class<? extends TileableObject>> getConnectableTypes() {
        return Arrays.asList(DirtRoad.class);
    }
}
