package com.mygdx.game.item.weapon.bow;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.weapon.RangedWeapon;
import com.mygdx.game.logic.Point;

import java.util.Random;

public class LongBow implements Bow, RangedWeapon {
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
    public void setCoordinates(Point point) {

    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }
}