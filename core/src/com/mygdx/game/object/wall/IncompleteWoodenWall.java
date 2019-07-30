package com.mygdx.game.object.wall;

import com.mygdx.game.Config;
import com.mygdx.game.builder.BuildingBlock;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.WorldObject;

public class IncompleteWoodenWall implements Wall, BuildingBlock<WoodenWall> {

    private float progress;
    private Point coordinates;

    public IncompleteWoodenWall(Point coordinates) {
        this.progress = 0f;
        this.coordinates = coordinates;
    }

    @Override
    public void addProgress(float percentage) {
        this.progress += percentage;
    }



    @Override
    public Class<WoodenWall> finish() {
        return WoodenWall.class;
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

    @Override
    public float getProgress() {
        return this.progress;
    }
}
