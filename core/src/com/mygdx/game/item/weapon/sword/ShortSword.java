package com.mygdx.game.item.weapon.sword;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.OneHandedItem;
import com.mygdx.game.item.category.Legendary;
import com.mygdx.game.item.category.Tier1;
import com.mygdx.game.item.weapon.Weapon;
import com.mygdx.game.logic.Point;

import java.util.Random;

public class ShortSword implements OneHandedItem, Sword, Tier1 {

    private Point coordinates = new Point(0,0);

    @Override
    public int getDamage() {
        return new Random().nextInt(4) + 2;
    }

    @Override
    public int getPrice() {
        return 100;
    }

    @Override
    public void onHit(Actor target) {

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
        return Config.Item.SHORT_SWORD_POWER;
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
