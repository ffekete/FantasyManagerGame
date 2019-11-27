package com.mygdx.game.actor.worker;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.faction.Alignment;

public class Smith extends AbstractActor implements Worker {

    public Smith() {
        setAlignment(Alignment.FRIENDLY);
    }

    @Override
    public String getActorClass() {
        return "Smith";
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.Humanoid;
    }
}
