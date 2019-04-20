package com.mygdx.game.item;

import com.mygdx.game.actor.Actor;

public interface Consumable extends Item {

    void consume(Actor actor);

}
