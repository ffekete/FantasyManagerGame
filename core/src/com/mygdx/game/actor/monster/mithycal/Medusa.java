package com.mygdx.game.actor.monster.mithycal;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.actor.CasterActor;
import com.mygdx.game.actor.MeleeActor;

import static com.mygdx.game.faction.Alignment.ENEMY;

public class Medusa extends AbstractActor implements CasterActor {

    public Medusa() {
        this.setAlignment(ENEMY);
    }

    @Override
    public boolean isHungry() {
        return false;
    }

    @Override
    public void increaseHunger(int amount) {

    }

    @Override
    public void decreaseHunger(int amount) {

    }

    @Override
    public boolean isSleepy() {
        return false;
    }

    @Override
    public void increaseSleepiness(int amount) {

    }

    @Override
    public void decreaseSleepiness(int amount) {

    }

    @Override
    public int getSleepinessLevel() {
        return 0;
    }

    @Override
    public int getHungerLevel() {
        return 0;
    }

    @Override
    public String getActorClass() {
        return "Medusa";
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.Medusa;
    }
}
