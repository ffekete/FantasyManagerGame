package com.mygdx.game.actor.monster;

import com.mygdx.game.actor.AbstractActor;

import static com.mygdx.game.faction.Alignment.ENEMY;

public class Goblin extends AbstractActor  {

    public Goblin() {
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
    public int getHungerLevel() {
        return 0;
    }
}
