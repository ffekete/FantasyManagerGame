package com.mygdx.game.item.shield;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.effect.AttackSpeedReduction;
import com.mygdx.game.effect.Effect;
import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.Craftable;
import com.mygdx.game.item.category.Tier1;
import com.mygdx.game.item.category.Tier3;
import com.mygdx.game.registry.EffectRegistry;

public class LionShield extends AbstractItem implements Shield, Tier3, Craftable {

    private Effect attackSpeedModifier = new AttackSpeedReduction(10);
    private EffectRegistry effectRegistry = EffectRegistry.INSTANCE;

    @Override
    public int getDefense() {
        return 10;
    }

    @Override
    public void onEquip(Actor actor) {
        effectRegistry.add(attackSpeedModifier, actor);
    }

    @Override
    public void onRemove(Actor actor) {
        effectRegistry.remove(attackSpeedModifier, actor);
    }

    @Override
    public int getPower() {
        return Config.Item.LARGE_SHIELD_POWER;
    }

    @Override
    public String getDescription() {
        return "Lion Shield, effective \n against arrows.";
    }

    @Override
    public String getName() {
        return "Large shield";
    }

    @Override
    public int getPrice() {
        return 80;
    }
}
