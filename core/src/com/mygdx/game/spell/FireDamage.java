package com.mygdx.game.spell;

import com.mygdx.game.actor.Actor;

public class FireDamage {

    private final Actor initiator;

    public FireDamage(Actor initiator) {
        this.initiator = initiator;
    }

    public void calculate(Actor target, int damage) {
        target.setHp(target.getHp() - damage);

        if (target.getHp() <= 0)
            target.die(initiator);
    }

}
