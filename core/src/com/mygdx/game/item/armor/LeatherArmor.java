package com.mygdx.game.item.armor;

import com.google.common.collect.ImmutableSet;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.Craftable;
import com.mygdx.game.item.category.Tier1;

import java.util.Set;

public class LeatherArmor extends AbstractItem implements Armor, Tier1, Craftable {

    @Override
    public int getDamageProtection() {
        return 6;
    }

    @Override
    public void onEquip(Actor actor) {

    }

    @Override
    public void onRemove(Actor actor) {

    }

    @Override
    public int getPower() {
        return Config.Item.LEATHER_ARMOR_POWER;
    }

    @Override
    public String getDescription() {
        return "Standard leather armor.";
    }

    @Override
    public String getName() {
        return "Leather armor";
    }

    @Override
    public int getPrice() {
        return 80;
    }

    @Override
    public Set<BodyType> getCompatibleBodyTypes() {
        return ImmutableSet.of(BodyType.Humanoid, BodyType.HumanoidSkeleton, BodyType.Orc);
    }

}
