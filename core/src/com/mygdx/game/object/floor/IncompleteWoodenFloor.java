package com.mygdx.game.object.floor;

import com.mygdx.game.Config;
import com.mygdx.game.builder.BuildingBlock;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.Obstacle;
import com.mygdx.game.object.TileableObject;
import com.mygdx.game.object.TileableWallObject;
import com.mygdx.game.object.wall.Wall;
import com.mygdx.game.object.wall.WoodenWall;

import java.util.Arrays;
import java.util.List;

public class IncompleteWoodenFloor implements Floor, BuildingBlock<WoodenFloor>, TileableFloorObject {

    private float progress;
    private Point coordinates;

    public IncompleteWoodenFloor(Point coordinates) {
        this.progress = 0f;
        this.coordinates = coordinates;
    }

    @Override
    public void addProgress(float percentage) {
        this.progress += percentage;
    }



    @Override
    public Class<WoodenFloor> finish() {
        return WoodenFloor.class;
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

    @Override
    public List<Class<? extends TileableObject>> getConnectableTypes() {
        return Arrays.asList(Floor.class);
    }
}
