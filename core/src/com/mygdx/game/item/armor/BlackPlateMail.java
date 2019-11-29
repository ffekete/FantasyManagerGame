package com.mygdx.game.item.armor;

import com.google.common.collect.ImmutableSet;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.monster.undead.Lich;
import com.mygdx.game.actor.monster.greenskins.Orc;
import com.mygdx.game.actor.monster.undead.SkeletonWarrior;
import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.Craftable;
import com.mygdx.game.item.category.Tier3;

import java.util.Set;

public class BlackPlateMail extends AbstractItem implements Armor, Tier3, Craftable {

    @Override
    public String getSimpleName() {
        return "blackPlateMailArmor";
    }

    @Override
    public int getDamageProtection() {
        return 8;
    }

    @Override
    public Set<BodyType> getCompatibleBodyTypes() {
        return ImmutableSet.of(BodyType.Humanoid, BodyType.HumanoidSkeleton, BodyType.Orc);
    }

    @Override
    public Set<Class<? extends Actor>> getAllowedClasses() {
        return ImmutableSet.of(Warrior.class, SkeletonWarrior.class, Lich.class, Orc.class);
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

    @Override
    public int getPrice() {
        return 1200;
    }
}
