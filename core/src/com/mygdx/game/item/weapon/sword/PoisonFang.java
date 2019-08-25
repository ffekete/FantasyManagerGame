package com.mygdx.game.item.weapon.sword;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.effect.Poison;
import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.OneHandedItem;
import com.mygdx.game.item.category.Legendary;
import com.mygdx.game.logic.Point;
import com.mygdx.game.registry.EffectRegistry;

import java.util.Random;

public class PoisonFang extends AbstractItem implements OneHandedItem, Sword, Legendary {

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
        EffectRegistry.INSTANCE.add(new Poison(2, 5, target, originatingActor), target);
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
        return Config.Item.POISON_FANG_POWER;
    }

    @Override
    public String getDescription() {
        return "Forged by the mighty goblin wich king.";
    }

    @Override
    public String getName() {
        return "Poison Fang";
    }
}
