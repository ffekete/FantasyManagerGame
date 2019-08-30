package com.mygdx.game.object.interactive;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.worker.Smith;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.AnimatedObject;
import com.mygdx.game.object.InteractiveObject;
import com.mygdx.game.object.Obstacle;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.furniture.Furniture;

import java.util.Random;

public class Smelter implements WorldObject, Obstacle, InteractiveObject, AnimatedObject, Furniture {

    private final float worldMapSize = 1f;

    private Point coordinates;

    private int phase = new Random().nextInt(3);

    public Smelter(Point point) {
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

    }

    @Override
    public boolean canInteract(Actor actor) {
        return Smith.class.isAssignableFrom(actor.getClass());
    }

    @Override
    public void finished(Actor actor) {

    }

    @Override
    public int getPhase() {
        return phase;
    }

    @Override
    public float getSpeed() {
        return 0.04f;
    }
}
