package com.mygdx.game.item.potion;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Consumable;

public class SmallHealingPotion implements Consumable, HealingPotion {

    private int x;
    private int y;

    @Override
    public void consume(Actor actor) {
        actor.setHp(Math.min(actor.getMaxHp(), actor.getHp() + Config.Item.HEALING_POTION_STRENGTH));
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
}
