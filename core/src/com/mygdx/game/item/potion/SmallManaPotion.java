package com.mygdx.game.item.potion;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.Consumable;
import com.mygdx.game.item.category.Tier1;

public class SmallManaPotion extends AbstractItem implements Consumable, ManaPotion, Tier1 {

    public static final String DESCRIPTION = "Adds " + Config.Item.MANA_POTION_STRENGTH + " to mana.";

    @Override
    public void consume(Actor actor) {
        actor.setMana(Math.min(actor.getMaxMana(), actor.getMana() + Config.Item.MANA_POTION_STRENGTH));
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public String getName() {
        return "Small mana potion";
    }
}
