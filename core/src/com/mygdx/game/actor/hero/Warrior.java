package com.mygdx.game.actor.hero;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.actor.MeleeActor;
import com.mygdx.game.actor.factory.SkillFocusFactory;

import static com.mygdx.game.faction.Alignment.FRIENDLY;

public class Warrior extends AbstractActor implements MeleeActor, Hero {

    public Warrior() {
        setAlignment(FRIENDLY);
        setSkillFocusDefinition(SkillFocusFactory.Warrior.create());
    }

    @Override
    public String getActorClass() {
        return "Warrior";
    }
}
