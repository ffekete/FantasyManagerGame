package com.mygdx.game.actor.monster.mithycal;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.actor.MeleeActor;

import static com.mygdx.game.faction.Alignment.ENEMY;

public class Cyclops extends AbstractActor implements MeleeActor {

    public Cyclops() {
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
        return "Cyclops";
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.Cyclops;
    }
}
