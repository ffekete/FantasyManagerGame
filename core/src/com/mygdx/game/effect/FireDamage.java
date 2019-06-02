package com.mygdx.game.effect;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.actor.Actor;

public class FireDamage implements Effect {

    private final int strength;
    private final Actor target;
    private final Actor originatingActor;

    private int duration;
    private float counter;

    public FireDamage(int strength, int duration, Actor target, Actor originatingActor) {
        this.strength = strength;
        this.duration = duration;
        this.target = target;
        this.counter = 60;
        this.originatingActor = originatingActor;
    }

    @Override
    public void update() {
        counter -= 60 * Gdx.graphics.getRawDeltaTime();

        if(counter <= 0) {
            counter = 60;
            duration--;
            target.setHp(target.getHp() - strength);
            if (target.getHp() <= 0) {
                target.die(originatingActor);
            }
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
