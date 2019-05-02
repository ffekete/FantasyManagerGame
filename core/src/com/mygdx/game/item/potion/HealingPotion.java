package com.mygdx.game.item.potion;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Consumable;

public interface HealingPotion extends Consumable {
    void consume(Actor actor);
}
