package com.mygdx.game.actor.worker;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.faction.Alignment;

public class Builder extends AbstractActor implements Worker {

    public Builder() {
        setAlignment(Alignment.FRIENDLY);
    }

    @Override
    public String getActorClass() {
        return "Builder";
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.Humanoid;
    }
}
