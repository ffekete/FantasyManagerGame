package com.mygdx.game.item.weapon;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Equipable;

public interface Weapon extends Equipable {
    int getDamage();
    int getPrice();
    void onHit(Actor target);
}
