package com.mygdx.game.actor.worker;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.faction.Alignment;

public class Shopkeeper extends AbstractActor implements Worker {

    public Shopkeeper() {
        setAlignment(Alignment.FRIENDLY);
    }

    @Override
    public String getActorClass() {
        return "Shopkeeper";
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.Humanoid;
    }
}
