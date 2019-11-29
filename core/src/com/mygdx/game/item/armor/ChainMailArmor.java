package com.mygdx.game.item.armor;

import com.google.common.collect.ImmutableSet;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.actor.hero.Ranger;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.monster.greenskins.Orc;
import com.mygdx.game.actor.monster.undead.Lich;
import com.mygdx.game.actor.monster.undead.Skeleton;
import com.mygdx.game.actor.monster.undead.SkeletonWarrior;
import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.Craftable;
import com.mygdx.game.item.category.Tier1;

import java.util.Set;

public class ChainMailArmor extends AbstractItem implements Armor, Tier1, Craftable {

    @Override
    public String getSimpleName() {
        return "chainMailArmor";
    }

    @Override
    public int getDamageProtection() {
        return 7;
    }

    @Override
    public void onEquip(Actor actor) {

    }

    @Override
    public Set<Class<? extends Actor>> getAllowedClasses() {
        return ImmutableSet.of(Warrior.class, Ranger.class, Orc.class, Skeleton.class, SkeletonWarrior.class, Lich.class);
    }

    @Override
    public Set<BodyType> getCompatibleBodyTypes() {
        return ImmutableSet.of(BodyType.Humanoid, BodyType.HumanoidSkeleton, BodyType.Orc);
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
