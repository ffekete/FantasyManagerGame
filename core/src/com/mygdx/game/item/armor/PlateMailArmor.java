package com.mygdx.game.item.armor;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.Craftable;
import com.mygdx.game.item.category.Tier2;

public class PlateMailArmor extends AbstractItem implements Armor, Tier2, Craftable {

    @Override
    public int getDamageProtection() {
        return 8;
    }

    @Override
    public void onEquip(Actor actor) {

    }

    @Override
    public void onRemove(Actor actor) {

    }

    @Override
    public int getPower() {
        return Config.Item.PLATE_MAIL_POWER;
    }

    @Override
    public String getDescription() {
        return "Standard plate mail.";
    }

    @Override
    public String getName() {
        return "Plate mail armor";
    }

    @Override
    public int getPrice() {
        return 500;
    }
}
