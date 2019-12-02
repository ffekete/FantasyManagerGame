package com.mygdx.game.item.armor;

import com.google.common.collect.ImmutableSet;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.actor.hero.*;
import com.mygdx.game.actor.monster.greenskins.Orc;
import com.mygdx.game.actor.monster.greenskins.OrcBoss;
import com.mygdx.game.actor.monster.greenskins.OrcShaman;
import com.mygdx.game.actor.monster.rats.RatAssassin;
import com.mygdx.game.actor.monster.rats.RatBrute;
import com.mygdx.game.actor.monster.rats.RatMan;
import com.mygdx.game.actor.monster.rats.RatSniper;
import com.mygdx.game.actor.monster.undead.*;
import com.mygdx.game.actor.monster.vampires.Vampire;
import com.mygdx.game.actor.monster.vampires.VampireMaster;
import com.mygdx.game.actor.monster.vampires.VampireThrall;
import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.Craftable;
import com.mygdx.game.item.category.Tier1;

import java.util.Set;

public class LeatherArmor extends AbstractItem implements Armor, Tier1, Craftable {

    @Override
    public String getSimpleName() {
        return "leatherArmor";
    }

    @Override
    public Set<Class<? extends Actor>> getAllowedClasses() {
        return ImmutableSet.of(Warrior.class,
                Ranger.class,
                Paladin.class,
                Rogue.class,
                Assassin.class,
                Druid.class,
                Priest.class,
                Skeleton.class,
                SkeletonWarrior.class,
                Lich.class,
                PowerLich.class,
                UndeadKnight.class,
                Orc.class,
                OrcBoss.class,
                OrcShaman.class,
                RatBrute.class,
                RatMan.class,
                RatAssassin.class,
                RatSniper.class,
                VampireThrall.class,
                Vampire.class,
                VampireMaster.class);
    }

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
