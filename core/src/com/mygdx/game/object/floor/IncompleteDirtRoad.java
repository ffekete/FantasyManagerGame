package com.mygdx.game.object.floor;

import com.mygdx.game.Config;
import com.mygdx.game.builder.BuildingBlock;
import com.mygdx.game.logic.Point;

public class IncompleteDirtRoad implements Floor, BuildingBlock<Road>, TileableFloorObject {

    private float progress;
    private Point coordinates;

    public IncompleteDirtRoad(Point coordinates) {
        this.progress = 0f;
        this.coordinates = coordinates;
    }

    @Override
    public void addProgress(float percentage) {
        this.progress += percentage;
    }



    @Override
    public Class<Road> finish() {
        return Road.class;
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
