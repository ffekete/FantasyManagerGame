package com.mygdx.game.item.potion;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.effect.Poison;
import com.mygdx.game.item.Consumable;
import com.mygdx.game.item.category.Tier1;
import com.mygdx.game.logic.Point;
import com.mygdx.game.registry.EffectRegistry;

public class SmallAntiVenomPotion implements Consumable, AntiVenomPotion, Tier1 {

    private Point coordinates = new Point(0, 0);

    private EffectRegistry effectRegistry = EffectRegistry.INSTANCE;

    @Override
    public void consume(Actor actor) {
        effectRegistry.removeAll(actor, Poison.class);
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
