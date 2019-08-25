package com.mygdx.game.item.weapon.sword;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.Craftable;
import com.mygdx.game.item.OneHandedItem;
import com.mygdx.game.item.category.Legendary;
import com.mygdx.game.item.category.Tier1;
import com.mygdx.game.item.weapon.Weapon;
import com.mygdx.game.logic.Point;

import java.util.Random;

public class ShortSword extends AbstractItem implements OneHandedItem, Sword, Tier1, Craftable {

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
        return Config.Item.SHORT_SWORD_POWER;
    }

    @Override
    public String getDescription() {
        return "Simple sharp sword.";
    }

    @Override
    public String getName() {
        return "Short sword";
    }
}
