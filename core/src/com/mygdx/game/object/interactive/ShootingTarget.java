package com.mygdx.game.object.interactive;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.InteractiveObject;
import com.mygdx.game.object.Obstacle;
import com.mygdx.game.object.TrainingObject;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.furniture.Furniture;

public class ShootingTarget implements WorldObject, InteractiveObject, Obstacle, Furniture, TrainingObject {

    private final float worldMapSize = 1f;

    private Point coordinates;

    public ShootingTarget(Point point) {
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
    public void finished(Actor actor) {

    }

    @Override
    public int getExperienceAmount() {
        return 20;
    }
}
