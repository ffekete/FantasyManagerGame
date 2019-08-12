package com.mygdx.game.object.decoration;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.Cuttable;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.item.resources.Resource;
import com.mygdx.game.item.resources.Wood;

import java.util.Random;

public class TreeV2 implements WorldObject, Decoration, Cuttable {

    private final float worldMapSize = new Random().nextFloat() / 2f + 1.5f;

    private Point coordinates;
    private float progress;

    public TreeV2(Point point) {
        coordinates = point;
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
        return worldMapSize;
    }

    @Override
    public void addProgress(float progress) {
        this.progress += progress;
    }

    @Override
    public Class<? extends Resource> finish(Actor actor) {

        return Wood.class;
    }

    @Override
    public boolean isFinished() {
        return progress >= 100f;
    }
}
