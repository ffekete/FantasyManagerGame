package com.mygdx.game.item.armor;

import com.google.common.collect.ImmutableSet;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.actor.monster.greenskins.CaveTroll;
import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.Craftable;
import com.mygdx.game.item.category.Tier2;

import java.util.Set;

public class TrollArmor extends AbstractItem implements Armor, Tier2, Craftable {

    @Override
    public Set<Class<? extends Actor>> getAllowedClasses() {
        return ImmutableSet.of(CaveTroll.class);
    }

    @Override
    public String getSimpleName() {
        return "trollArmor";
    }

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
        return "Troll armor.";
    }

    @Override
    public String getName() {
        return "Troll armor";
    }

    @Override
    public int getPrice() {
        return 30;
    }

    @Override
    public Set<BodyType> getCompatibleBodyTypes() {
        return ImmutableSet.of(BodyType.Troll);
    }

}
