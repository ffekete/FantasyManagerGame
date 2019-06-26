package com.mygdx.game.item.weapon.sword;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.OneHandedItem;
import com.mygdx.game.item.category.Tier2;
import com.mygdx.game.item.category.Tier4;
import com.mygdx.game.logic.Point;

import java.util.Random;

public class ShortSwordPlusFour implements OneHandedItem, Sword, Tier4 {

    private Point coordinates = new Point(0,0);

    @Override
    public int getDamage() {
        return new Random().nextInt(4) + 2 + 4;
    }

    @Override
    public int getPrice() {
        return 2500;
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
        return Config.Item.SHORT_SWORD_PLUS_FOUR_POWER;
    }

    @Override
    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public int getX() {
        return this.coordinates.getX();
    }

    @Override
    public int getY() {
        return this.coordinates.getY();
    }
}