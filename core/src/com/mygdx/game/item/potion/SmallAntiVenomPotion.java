package com.mygdx.game.item.potion;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.effect.Poison;
import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.Consumable;
import com.mygdx.game.item.category.Tier1;
import com.mygdx.game.registry.EffectRegistry;

public class SmallAntiVenomPotion extends AbstractItem implements Consumable, AntiVenomPotion, Tier1 {

    private EffectRegistry effectRegistry = EffectRegistry.INSTANCE;

    @Override
    public void consume(Actor actor) {
        effectRegistry.removeAll(actor, Poison.class);
    }

    @Override
    public String getDescription() {
        return "Cures all poisons.";
    }

    @Override
    public String getName() {
        return "Antivenom potion";
    }
}
