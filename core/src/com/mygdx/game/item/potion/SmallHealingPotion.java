package com.mygdx.game.item.potion;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.Consumable;
import com.mygdx.game.item.category.Tier1;

public class SmallHealingPotion extends AbstractItem implements Consumable, HealingPotion, Tier1 {

    @Override
    public void consume(Actor actor) {
        actor.setHp(Math.min(actor.getMaxHp(), actor.getHp() + Config.Item.HEALING_POTION_STRENGTH));
    }

    @Override
    public String getDescription() {
        return "Heals " + Config.Item.HEALING_POTION_STRENGTH + " amount of health.";
    }

    @Override
    public String getName() {
        return "Small healing potion";
    }

    @Override
    public int getPrice() {
        return 20;
    }
}
