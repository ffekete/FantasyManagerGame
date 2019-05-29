package com.mygdx.game.item.potion;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Consumable;
import com.mygdx.game.item.category.Tier1;
import com.mygdx.game.logic.Point;

public class SmallHealingPotion implements Consumable, HealingPotion, Tier1 {

    private Point coordinates = new Point(0,0);

    @Override
    public void consume(Actor actor) {
        actor.setHp(Math.min(actor.getMaxHp(), actor.getHp() + Config.Item.HEALING_POTION_STRENGTH));
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
