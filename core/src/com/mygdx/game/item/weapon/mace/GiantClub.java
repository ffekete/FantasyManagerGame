package com.mygdx.game.item.weapon.mace;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.Craftable;
import com.mygdx.game.item.category.Tier3;
import com.mygdx.game.item.weapon.TwohandedWeapon;

import java.util.Random;

public class GiantClub extends AbstractItem implements TwohandedWeapon, Mace, Tier3, Craftable {

    @Override
    public int getDamage() {
        return new Random().nextInt(8) + 8;
    }

    @Override
    public int getPrice() {
        return 100;
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
        return Config.Item.FLAMBERGE_POWER;
    }

    @Override
    public String getDescription() {
        return "Two handed deadly sword.";
    }

    @Override
    public String getName() {
        return "Flamberge";
    }
}
