package com.mygdx.game.object.furniture;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.InteractiveObject;
import com.mygdx.game.object.Obstacle;
import com.mygdx.game.object.OwnedObject;
import com.mygdx.game.object.WorldObject;

public class WoodenBed implements WorldObject, Obstacle, InteractiveObject, Bed, OwnedObject, Furniture {

    private Actor owner;
    private Point coordinates;

    public WoodenBed(Point coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public void onInteract(Actor actor) {
        // sleep
    }

    @Override
    public boolean canInteract(Actor actor) {
        return actor.equals(owner);
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

    @Override
    public Actor getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Actor actor) {
        this.owner = actor;
    }
}
