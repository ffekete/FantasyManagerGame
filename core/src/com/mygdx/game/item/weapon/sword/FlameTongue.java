package com.mygdx.game.item.weapon.sword;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.effect.FireDamage;
import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.category.Legendary;
import com.mygdx.game.item.weapon.OnehandedWeapon;
import com.mygdx.game.logic.Point;
import com.mygdx.game.registry.EffectRegistry;

import java.util.Random;

public class FlameTongue extends AbstractItem implements OnehandedWeapon, Sword, Legendary {

    private final EffectRegistry effectRegistry = EffectRegistry.INSTANCE;

    private Point coordinates = new Point(0,0);

    @Override
    public int getDamage() {
        return new Random().nextInt(3) + 2;
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public void onHit(Actor target, Actor originatingActor) {
        effectRegistry.add(new FireDamage(1, 1, target, originatingActor), target);
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
        return Config.Item.FLAME_TONGUE_POWER;
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getName() {
        return "";
    }
}
