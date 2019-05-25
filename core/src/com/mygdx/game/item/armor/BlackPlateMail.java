package com.mygdx.game.item.armor;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.category.Tier3;
import com.mygdx.game.logic.Point;

public class BlackPlateMail implements Armor, Tier3 {

    private Point coordinates = new Point(0,0);

    @Override
    public int getDamageProtection() {
        return 8;
    }

    @Override
    public void onEquip(Actor actor) {

    }

    @Override
    public void onRemove(Actor actor) {

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
}
