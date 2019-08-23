package com.mygdx.game.actor.wildlife;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.actor.MeleeActor;

import static com.mygdx.game.faction.Alignment.NEUTRAL;

public class Wolf extends AbstractActor implements MeleeActor {

    public Wolf() {
        this.setAlignment(NEUTRAL);
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
        return "Wolf";
    }
}
