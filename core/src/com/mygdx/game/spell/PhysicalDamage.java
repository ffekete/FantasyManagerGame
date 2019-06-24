package com.mygdx.game.spell;

import com.mygdx.game.actor.Actor;

public class PhysicalDamage {

    private final Actor initiator;

    public PhysicalDamage(Actor initiator) {
        this.initiator = initiator;
    }

    public void calculate(Actor target, int damage) {
        target.setHp(target.getHp() - damage);

        if (target.getHp() <= 0)
            target.die(initiator);
    }

}
