package com.mygdx.game.actor.hero;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.actor.MeleeActor;
import com.mygdx.game.actor.factory.SkillFocusFactory;
import com.mygdx.game.faction.Alignment;

public class Rogue extends AbstractActor implements MeleeActor, Hero {

    public Rogue() {
        setAlignment(Alignment.FRIENDLY);
        setSkillFocusDefinition(SkillFocusFactory.Warrior.create());
    }
}
