package com.mygdx.game.item.armor;

import com.google.common.collect.ImmutableSet;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.actor.hero.Ranger;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.monster.Lich;
import com.mygdx.game.actor.monster.Orc;
import com.mygdx.game.actor.monster.Skeleton;
import com.mygdx.game.actor.monster.SkeletonWarrior;
import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.Craftable;
import com.mygdx.game.item.category.Tier2;

import java.util.Set;

public class PlateMailArmor extends AbstractItem implements Armor, Tier2, Craftable {

    @Override
    public Set<Class<? extends Actor>> getAllowedClasses() {
        return ImmutableSet.of(Warrior.class, Ranger.class, Orc.class, SkeletonWarrior.class, Lich.class);
    }

    @Override
    public String getSimpleName() {
        return "plateMailArmor";
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

    @Override
    public Set<BodyType> getCompatibleBodyTypes() {
        return ImmutableSet.of(BodyType.Humanoid, BodyType.HumanoidSkeleton, BodyType.Orc);
    }

}
