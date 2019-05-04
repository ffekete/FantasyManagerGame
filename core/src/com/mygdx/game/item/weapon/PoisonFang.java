package com.mygdx.game.item.weapon;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.effect.Poison;
import com.mygdx.game.item.OneHandedItem;
import com.mygdx.game.registry.EffectRegistry;

import java.util.Random;

public class PoisonFang implements Weapon, OneHandedItem {

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
        System.out.println("Poisoning target");
        EffectRegistry.INSTANCE.add(new Poison(2, 5, target), target);
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
