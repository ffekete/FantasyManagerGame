package com.mygdx.game.actor.hero;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.actor.CasterActor;

import static com.mygdx.game.faction.Alignment.FRIENDLY;

public class Witch extends AbstractActor implements CasterActor, Hero {

    public Witch() {
        setAlignment(FRIENDLY);

        // todo fill
    }

    @Override
    public String getActorClass() {
        return "Witch";
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.Humanoid;
    }
}
