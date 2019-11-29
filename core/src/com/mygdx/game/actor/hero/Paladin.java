package com.mygdx.game.actor.hero;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.actor.MeleeActor;
import com.mygdx.game.actor.factory.SkillFocusFactory;

import static com.mygdx.game.faction.Alignment.FRIENDLY;

public class Paladin extends AbstractActor implements MeleeActor, Hero {

    public Paladin() {
        setAlignment(FRIENDLY);
    }

    @Override
    public String getActorClass() {
        return "Paladin";
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.Humanoid;
    }
}
