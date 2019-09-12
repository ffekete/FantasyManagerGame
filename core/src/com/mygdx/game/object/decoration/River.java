package com.mygdx.game.object.decoration;

import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class River implements WorldObject, Decoration, NoViewBlockinObstacle, AnimatedTiledObject {

    private Point coordinates;
    private float size = new Random().nextFloat() / 2f + 0.5f;

    private float phase = 0f;

    public River(Point coordinates) {
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
        return this.coordinates;
    }

    @Override
    public float getWorldMapSize() {
        return size;
    }

    @Override
    public int getPhase() {
        phase = (phase + 0.05f) % 2f;
        return (int)phase;
    }

    @Override
    public List<Class<? extends TileableObject>> getConnectableTypes() {
        return Arrays.asList(River.class);
    }
}
