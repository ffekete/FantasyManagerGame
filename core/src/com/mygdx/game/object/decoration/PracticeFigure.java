package com.mygdx.game.object.decoration;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Item;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.*;
import com.mygdx.game.object.furniture.Furniture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PracticeFigure implements WorldObject, InteractiveObject, Obstacle, AnimatedObject, Furniture, TrainingObject {

    private final float worldMapSize = 1f;

    private Point coordinates;

    private int phase = new Random().nextInt(3);

    public PracticeFigure(Point point) {
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
    public void onInteract(Actor actor) {
        actor.addExperiencePoints(getExperienceAmount());
    }

    @Override
    public boolean canInteract(Actor actor) {
        return true;
    }

    @Override
    public int getExperienceAmount() {
        return 20;
    }

    @Override
    public int getPhase() {
        return phase;
    }

    @Override
    public float getSpeed() {
        return 0.1f;
    }
}
