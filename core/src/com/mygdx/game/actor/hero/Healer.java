package com.mygdx.game.actor.hero;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.actor.CasterActor;
import com.mygdx.game.actor.factory.SkillFocusFactory;
import com.mygdx.game.item.spelltome.SpellTome;
import com.mygdx.game.spell.offensive.FireBall;
import com.mygdx.game.spell.offensive.FireBolt;
import com.mygdx.game.spell.offensive.Slow;

import static com.mygdx.game.faction.Alignment.FRIENDLY;

public class Healer extends AbstractActor implements CasterActor, Hero {

    public Healer() {
        setAlignment(FRIENDLY);

        // todo fill
    }

    @Override
    public String getActorClass() {
        return "Healer";
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.Humanoid;
    }
}
