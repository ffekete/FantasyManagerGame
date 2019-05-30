package com.mygdx.game.item.shield;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.category.Tier1;
import com.mygdx.game.logic.Point;

public class SmallShiled implements Shield, Tier1 {

    private int defense = 5;
    private Point coordinates = new Point(0,0);

    @Override
    public void onEquip(Actor actor) {

    }

    @Override
    public void onRemove(Actor actor) {

    }

    @Override
    public int getPower() {
        return Config.Item.SMALL_SHIELD_POWER;
    }

    @Override
    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public int getX() {
        return this.coordinates.getX();
    }

    @Override
    public int getY() {
        return this.coordinates.getY();
    }

    @Override
    public int getDefense() {
        return defense;
    }
}
