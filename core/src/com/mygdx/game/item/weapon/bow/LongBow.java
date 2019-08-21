package com.mygdx.game.item.weapon.bow;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Craftable;
import com.mygdx.game.item.category.Tier1;
import com.mygdx.game.item.weapon.RangedWeapon;
import com.mygdx.game.item.weapon.TwohandedWeapon;
import com.mygdx.game.logic.Point;

import java.util.Random;

public class LongBow implements Bow, Tier1, TwohandedWeapon, Craftable {

    private Point coordinates;

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
        this.coordinates = point;
    }

    @Override
    public int getX() {
        return coordinates.getX();
    }

    @Override
    public int getY() {
        return coordinates.getY();
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
