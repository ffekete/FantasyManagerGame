package com.mygdx.game.item.armor;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.Craftable;
import com.mygdx.game.item.category.Tier1;

public class ChainMailArmor extends AbstractItem implements Armor, Tier1, Craftable {

    @Override
    public int getDamageProtection() {
        return 7;
    }

    @Override
    public void onEquip(Actor actor) {

    }

    @Override
    public void onRemove(Actor actor) {

    }

    @Override
    public int getPower() {
        return Config.Item.CHAIN_MAIL_POWER;
    }

    @Override
    public String getDescription() {
        return "Standard chain mail.";
    }

    @Override
    public String getName() {
        return "Chain mail armor";
    }

    @Override
    public int getPrice() {
        return 100;
    }
}
