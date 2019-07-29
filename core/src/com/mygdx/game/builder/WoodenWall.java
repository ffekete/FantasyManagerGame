package com.mygdx.game.builder;

import com.mygdx.game.Config;
import com.mygdx.game.logic.Point;
import com.mygdx.game.map.worldmap.WorldMapTile;
import com.mygdx.game.object.WorldObject;

public class WoodenWall implements BuildingBlock, WorldObject {


    private float progress;
    private Point coordinates;

    public WoodenWall() {
        this.progress = 0f;
    }

    @Override
    public void addProgress(float percentage) {
        this.progress += percentage;
    }



    @Override
    public WorldMapTile finish() {
        return WorldMapTile.WOODEN_WALL;
    }

    @Override
    public boolean isFinished() {
        return progress >= 100f;
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
