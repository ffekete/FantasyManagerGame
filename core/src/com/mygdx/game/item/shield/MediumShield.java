package com.mygdx.game.item.shield;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.effect.AttackSpeedReduction;
import com.mygdx.game.effect.Effect;
import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.Craftable;
import com.mygdx.game.item.category.Tier1;
import com.mygdx.game.registry.EffectRegistry;

public class MediumShield extends AbstractItem implements Shield, Tier1, Craftable {

    private Effect attackSpeedReduction = new AttackSpeedReduction(-5);
    private EffectRegistry effectRegistry = EffectRegistry.INSTANCE;

    @Override
    public int getDefense() {
        return 6;
    }

    @Override
    public void onEquip(Actor actor) {
        effectRegistry.add(attackSpeedReduction, actor);
    }

    @Override
    public void onRemove(Actor actor) {
        effectRegistry.remove(attackSpeedReduction, actor);
    }

    @Override
    public int getPower() {
        return Config.Item.MEDIUM_SHIELD_POWER;
    }

    @Override
    public String getDescription() {
        return "Medium sized shield, effective \n against arrows.";
    }

    @Override
    public String getName() {
        return "Medium shield";
    }

    @Override
    public int getPrice() {
        return 50;
    }
}
