package com.mygdx.game.actor.monster;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.actor.MeleeActor;
import com.mygdx.game.item.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mygdx.game.faction.Alignment.ENEMY;

public class Orc extends AbstractActor implements MeleeActor {

    public Orc() {
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
        return "Orc warrior";
    }
}
