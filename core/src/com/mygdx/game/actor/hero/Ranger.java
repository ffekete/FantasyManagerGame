package com.mygdx.game.actor.hero;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.actor.MeleeActor;
import com.mygdx.game.actor.factory.SkillFocusFactory;
import com.mygdx.game.faction.Alignment;

public class Ranger extends AbstractActor implements MeleeActor, Hero {

    public Ranger() {
        setAlignment(Alignment.FRIENDLY);
        setSkillFocusDefinition(SkillFocusFactory.Ranger.create());
    }

    @Override
    public String getActorClass() {
        return "Ranger";
    }

    @Override
    public int getVisibilityRange() {
        return 10;
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.Humanoid;
    }
}
