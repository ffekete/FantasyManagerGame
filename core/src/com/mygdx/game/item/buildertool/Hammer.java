package com.mygdx.game.item.buildertool;

import com.mygdx.game.item.Item;
import com.mygdx.game.logic.Point;

public class Hammer implements Item {

    @Override
    public void setCoordinates(Point point) {

    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public String getDescription() {
        return "Pain old hammer";
    }

    @Override
    public String getName() {
        return "Hammer";
    }
}
