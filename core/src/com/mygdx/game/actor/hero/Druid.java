package com.mygdx.game.actor.hero;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.actor.CasterActor;

import static com.mygdx.game.faction.Alignment.FRIENDLY;

public class Druid extends AbstractActor implements CasterActor, Hero {

    public Druid() {
        setAlignment(FRIENDLY);

        // todo fill
    }

    @Override
    public String getActorClass() {
        return "Druid";
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.Humanoid;
    }
}
