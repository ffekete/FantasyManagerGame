package com.mygdx.game.item.shield;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.Craftable;
import com.mygdx.game.item.category.Tier1;
import com.mygdx.game.logic.Point;

public class SmallShiled extends AbstractItem implements Shield, Tier1, Craftable {

    private int defense = 5;

    @Override
    public void onEquip(Actor actor) {

    }

    @Override
    public void onRemove(Actor actor) {

    }

    @Override
    public int getPower() {
        return Config.Item.SMALL_SHIELD_POWER;
    }

    @Override
    public String getDescription() {
        return "Small wooden shield.";
    }

    @Override
    public String getName() {
        return "Small shield";
    }

    @Override
    public int getDefense() {
        return defense;
    }
}
