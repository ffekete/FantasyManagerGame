package com.mygdx.game.item.armor;

import com.google.common.collect.ImmutableSet;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.actor.hero.Paladin;
import com.mygdx.game.actor.hero.Ranger;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.monster.greenskins.Orc;
import com.mygdx.game.actor.monster.greenskins.OrcBoss;
import com.mygdx.game.actor.monster.rats.RatBrute;
import com.mygdx.game.actor.monster.rats.RatMan;
import com.mygdx.game.actor.monster.undead.Lich;
import com.mygdx.game.actor.monster.undead.PowerLich;
import com.mygdx.game.actor.monster.undead.SkeletonWarrior;
import com.mygdx.game.actor.monster.undead.UndeadKnight;
import com.mygdx.game.actor.monster.vampires.Vampire;
import com.mygdx.game.actor.monster.vampires.VampireMaster;
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
        return 12;
    }

    @Override
    public Set<BodyType> getCompatibleBodyTypes() {
        return ImmutableSet.of(BodyType.Humanoid, BodyType.HumanoidSkeleton, BodyType.Orc);
    }

    @Override
    public Set<Class<? extends Actor>> getAllowedClasses() {
        return ImmutableSet.of(Warrior.class,
                Ranger.class,
                Paladin.class,
                SkeletonWarrior.class,
                Lich.class,
                PowerLich.class,
                UndeadKnight.class,
                Orc.class,
                OrcBoss.class,
                RatBrute.class,
                RatMan.class,
                Vampire.class,
                VampireMaster.class);
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
