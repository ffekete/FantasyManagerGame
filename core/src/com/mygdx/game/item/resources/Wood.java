package com.mygdx.game.item.resources;

import com.mygdx.game.item.Item;
import com.mygdx.game.item.resources.Resource;
import com.mygdx.game.logic.Point;

public class Wood implements Item, Resource {

    private Point coordinates;

    public Wood(Point coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public void setCoordinates(Point point) {
        coordinates = point;
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
    public String getDescription() {
        return "Simple wood resource";
    }

    @Override
    public String getName() {
        return null;
    }
}
