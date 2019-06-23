package com.mygdx.game.actor.hero;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.actor.factory.SkillFocusFactory;
import com.mygdx.game.faction.Alignment;

public class Rogue extends AbstractActor implements MeleeHero {

    public Rogue() {
        setAlignment(Alignment.FRIENDLY);
        setSkillFocusDefinition(SkillFocusFactory.Warrior.create());
    }
}
