package com.mygdx.game.object.furniture;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.*;

import java.util.Arrays;
import java.util.List;

public class ShopkeepersDesk implements WorldObject, InteractiveObject, OwnedObject, Furniture, NoViewBlockinObstacle, TileableFurnitureObject {

    private Actor owner;
    private Point coordinates;

    public ShopkeepersDesk(Point coordinates) {
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
    public void finished(Actor actor) {

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

    @Override
    public List<Class<? extends TileableObject>> getConnectableTypes() {
        return Arrays.asList(IncompleteShopkeepersDesk.class, ShopkeepersDesk.class);
    }
}
