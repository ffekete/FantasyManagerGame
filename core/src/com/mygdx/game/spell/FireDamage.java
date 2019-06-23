package com.mygdx.game.spell;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.component.skill.MagicSkill;

public class FireDamage {

    private final Actor initiator;

    public FireDamage(Actor initiator) {
        this.initiator = initiator;
    }

    public void calculate(Actor target, int damage) {
        target.setHp(target.getHp() - damage - initiator.getSkillLevel(MagicSkill.FireMagic));

        if (target.getHp() <= 0)
            target.die(initiator);
    }

}
