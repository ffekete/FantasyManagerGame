package com.mygdx.game.item.weapon.twohandedsword;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.Craftable;
import com.mygdx.game.item.category.Tier1;
import com.mygdx.game.item.weapon.TwohandedWeapon;
import com.mygdx.game.item.weapon.sword.Sword;

import java.util.Random;

public class FlambergePlusOne extends AbstractItem implements TwohandedWeapon, TwoHandedSword, Sword, Tier1, Craftable {

    @Override
    public int getDamage() {
        return new Random().nextInt(8) + 2 + 1;
    }

    @Override
    public int getPrice() {
        return 500;
    }

    @Override
    public void onHit(Actor target, Actor originatingActor) {

    }

    @Override
    public int getRange() {
        return 1;
    }

    @Override
    public void onEquip(Actor actor) {

    }

    @Override
    public void onRemove(Actor actor) {
        
    }

    @Override
    public int getPower() {
        return Config.Item.FLAMBERGE_POWER + 1;
    }

    @Override
    public String getDescription() {
        return "Two handed deadly sword.";
    }

    @Override
    public String getName() {
        return "Flamberge +1";
    }
}
