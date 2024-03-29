package com.mygdx.game.item.weapon.staff;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.Craftable;
import com.mygdx.game.item.category.Tier1;
import com.mygdx.game.item.weapon.TwohandedWeapon;
import com.mygdx.game.logic.Point;

import java.util.Random;

public class JadeStaff extends AbstractItem implements Staff, Tier1, TwohandedWeapon, Craftable {

    private Point coordinates;

    @Override
    public int getDamage() {
        return new Random().nextInt(3) + 1;
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
        return 1;
    }

    @Override
    public void onEquip(Actor target) {

    }

    @Override
    public void onRemove(Actor actor) {

    }

    @Override
    public int getPower() {
        return Config.Item.JADE_STAFF_POWER;
    }

    @Override
    public String getDescription() {
        return "Staff with a jade on top.";
    }

    @Override
    public String getName() {
        return "Jade staff";
    }
}
