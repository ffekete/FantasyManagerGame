package com.mygdx.game.object.furniture;

import com.mygdx.game.builder.BuildingBlock;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.Obstacle;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.interactive.Anvil;
import com.mygdx.game.object.interactive.Smelter;

public class IncompleteSmelter implements WorldObject, Obstacle, Furniture, BuildingBlock {

    private Point coordinates;
    private float progress = 0.0f;

    public IncompleteSmelter(Point coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public void addProgress(float percentage) {
        this.progress += percentage;
    }

    @Override
    public Class finish() {
        return Smelter.class;
    }

    @Override
    public boolean isFinished() {
        return progress >= 100f;
    }

    @Override
    public float getProgress() {
        return progress;
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
        return 1.f;
    }
}
