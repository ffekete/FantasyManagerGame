package com.mygdx.game.item;

import com.mygdx.game.actor.Actor;

public interface Equipable extends Item {
    void onEquip(Actor actor);
    void onRemove(Actor actor);
}
