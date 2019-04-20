package com.mygdx.game.item;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;

public class Bread implements Food {

    private int x;
    private int y;

    @Override
    public void consume(Actor actor) {
        actor.decreaseHunger(Config.Item.BREAD_HUNGER_LEVEL);
    }

    @Override
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }


    @Override
    public int getNutritionAmount() {
        return Config.Item.BREAD_HUNGER_LEVEL;
    }
}
