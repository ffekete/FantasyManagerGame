package com.mygdx.game.effect;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.actor.Actor;

public class Poison implements Effect {

    private int stregth;
    private int duration;
    private float counter;
    private Actor target;
    private Actor originatingActor;

    public Poison(int stregth, int duration, Actor target, Actor originatingActor) {
        this.stregth = stregth;
        this.duration = duration;
        this.originatingActor = originatingActor;
        counter = 60;
        this.target = target;
    }

    @Override
    public void update() {
        counter -= 60 * Gdx.graphics.getDeltaTime();
        if (counter <= 0) {
            counter = 60;
            duration -= 1;

            target.setHp(target.getHp() - stregth);
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
        return stregth;
    }


}
