package com.mygdx.game.actor.hero;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.actor.factory.SkillFocusFactory;
import com.mygdx.game.faction.Alignment;

public class Ranger extends AbstractActor implements MeleeHero {

    public Ranger() {
        setAlignment(Alignment.FRIENDLY);
        setSkillFocusDefinition(SkillFocusFactory.Ranger.create());
    }

}
