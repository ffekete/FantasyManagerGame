package com.mygdx.game.item.resources;

import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.Item;
import com.mygdx.game.logic.Point;

public class Wood extends AbstractItem implements Item, Resource {

    public Wood(Point coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String getDescription() {
        return "Simple wood resource";
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getPrice() {
        return 3;
    }
}
