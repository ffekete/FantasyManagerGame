package com.mygdx.game.actor.hero;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.actor.CasterActor;

import static com.mygdx.game.faction.Alignment.FRIENDLY;

public class Necromancer extends AbstractActor implements CasterActor, Hero {

    public Necromancer() {
        setAlignment(FRIENDLY);

        // todo fill
    }

    @Override
    public String getActorClass() {
        return "Necromancer";
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.Humanoid;
    }
}
