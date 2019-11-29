package com.mygdx.game.actor.hero;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.actor.MeleeActor;
import com.mygdx.game.actor.factory.SkillFocusFactory;
import com.mygdx.game.faction.Alignment;

public class Assassin extends AbstractActor implements MeleeActor, Hero {

    public Assassin() {
        setAlignment(Alignment.FRIENDLY);
    }

    @Override
    public String getActorClass() {
        return "Assassin";
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.Humanoid;
    }
}
