package com.mygdx.game.item.food;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.category.Tier1;
import com.mygdx.game.logic.Point;

public class Bread implements Food, Tier1 {

    private Point coordinates = new Point(0,0);

    @Override
    public void consume(Actor actor) {
        actor.decreaseHunger(Config.Item.BREAD_HUNGER_LEVEL);
    }

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
    public int getNutritionAmount() {
        return Config.Item.BREAD_HUNGER_LEVEL;
    }
}
