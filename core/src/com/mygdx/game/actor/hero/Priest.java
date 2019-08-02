package com.mygdx.game.actor.hero;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.actor.CasterActor;
import com.mygdx.game.actor.factory.SkillFocusFactory;
import com.mygdx.game.faction.Alignment;

public class Priest extends AbstractActor implements CasterActor, Hero {

    public Priest() {
        setAlignment(Alignment.FRIENDLY);
        setSkillFocusDefinition(SkillFocusFactory.Priest.create());
    }

    @Override
    public String getActorClass() {
        return "Priest";
    }
}
