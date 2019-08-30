package com.mygdx.game.item.weapon.bow;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.Craftable;
import com.mygdx.game.item.category.Tier1;
import com.mygdx.game.item.weapon.TwohandedWeapon;

import java.util.Random;

public class LongBow extends AbstractItem implements Bow, Tier1, TwohandedWeapon, Craftable {

    @Override
    public int getDamage() {
        return new Random().nextInt(4) + 4;
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public void onHit(Actor target, Actor originatingActor) {

    }

    @Override
    public int getRange() {
        return Config.ATTACK_DISTANCE;
    }

    @Override
    public void onEquip(Actor target) {

    }

    @Override
    public void onRemove(Actor actor) {

    }

    @Override
    public int getPower() {
        return Config.Item.LONGBOW_POWER;
    }

    @Override
    public String getDescription() {
        return "Simple longbow.";
    }

    @Override
    public String getName() {
        return "Longbow";
    }
}
