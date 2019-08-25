package com.mygdx.game.item.armor;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.Craftable;
import com.mygdx.game.item.category.Tier3;
import com.mygdx.game.logic.Point;

public class BlackPlateMail extends AbstractItem implements Armor, Tier3, Craftable {

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
        return Config.Item.BLACK_PLATE_MAIL_POWER;
    }

    @Override
    public String getDescription() {
        return "Forged in the Black Mountains.";
    }

    @Override
    public String getName() {
        return "Black plate mail";
    }
}
