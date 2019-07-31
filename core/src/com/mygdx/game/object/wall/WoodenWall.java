package com.mygdx.game.object.wall;

import com.mygdx.game.Config;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.Obstacle;
import com.mygdx.game.object.TileableObject;
import com.mygdx.game.object.WorldObject;

public class WoodenWall implements Wall, WorldObject, TileableObject, Obstacle {

    private Point coordinates;

    public WoodenWall(Point coordinates) {
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
        return Config.Object.WOODEN_WALL_WORLD_MAP_SIZE;
    }
}
