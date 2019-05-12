package com.mygdx.game.item.weapon;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.OneHandedItem;

import java.util.Random;

public class ShortSword implements Weapon, OneHandedItem {

    private int x = 0;
    private int y = 0;

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
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }
}
