package com.mygdx.game.item;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.Point;

public abstract class AbstractItem implements Item {

    protected Point coordinates;

    @Override
    public void setCoordinates(Point point) {
        this.coordinates = point;
    }

    @Override
    public int getX() {
        return coordinates.getX();
    }

    @Override
    public int getY() {
        return coordinates.getY();
    }

    @Override
    public void pickedUp(Actor actor) {
        actor.getInventory().add(this);
    }
}
