package com.mygdx.game.item.weapon.sword;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.effect.FireDamage;
import com.mygdx.game.item.OneHandedItem;
import com.mygdx.game.item.weapon.Weapon;
import com.mygdx.game.registry.EffectRegistry;

import java.util.Random;

public class FlameTongue implements OneHandedItem, Sword {

    private final EffectRegistry effectRegistry = EffectRegistry.INSTANCE;

    @Override
    public int getDamage() {
        return new Random().nextInt(3) + 2;
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public void onHit(Actor target) {
        effectRegistry.add(new FireDamage(1, 1, target), target);
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
