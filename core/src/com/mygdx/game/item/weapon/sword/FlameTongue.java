package com.mygdx.game.item.weapon.sword;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.effect.FireDamage;
import com.mygdx.game.item.OneHandedItem;
import com.mygdx.game.item.category.Legendary;
import com.mygdx.game.item.weapon.Weapon;
import com.mygdx.game.logic.Point;
import com.mygdx.game.registry.EffectRegistry;

import java.util.Random;

public class FlameTongue implements OneHandedItem, Sword, Legendary {

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
    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
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
        return "";
    }

    @Override
    public String getName() {
        return "";
    }
}
