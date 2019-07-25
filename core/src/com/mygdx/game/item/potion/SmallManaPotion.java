package com.mygdx.game.item.potion;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Consumable;
import com.mygdx.game.item.category.Tier1;
import com.mygdx.game.logic.Point;

public class SmallManaPotion implements Consumable, ManaPotion, Tier1 {

    private Point coordinates = new Point(0,0);

    @Override
    public void consume(Actor actor) {
        actor.setMana(Math.min(actor.getMaxMana(), actor.getMana() + Config.Item.MANA_POTION_STRENGTH));
    }

    @Override
    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
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
