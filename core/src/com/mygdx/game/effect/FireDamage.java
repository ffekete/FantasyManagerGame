package com.mygdx.game.effect;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.actor.Actor;

public class FireDamage implements Effect {

    private final int strength;
    private final Actor target;

    private int duration;
    private float counter;

    public FireDamage(int strength, int duration, Actor target) {
        this.strength = strength;
        this.duration = duration;
        this.target = target;
        this.counter = 60;
    }

    @Override
    public void update() {
        counter -= 60 * Gdx.graphics.getDeltaTime();

        if(counter <= 0) {
            counter = 60;
            duration--;
            target.setHp(target.getHp() - strength);
        }
    }

    @Override
    public boolean isOver() {
        return duration == 0;
    }

    @Override
    public int getPower() {
        return strength;
    }
}
