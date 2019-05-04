package com.mygdx.game.effect;

import com.mygdx.game.actor.Actor;

public class Poison implements Effect {

    private int stregth;
    private int duration;
    private Actor target;

    public Poison(int stregth, int duration, Actor target) {
        this.stregth = stregth;
        this.duration = duration;
        this.target = target;
    }

    @Override
    public void update() {
        System.out.println(target + " was hit by poison: " + stregth);
        duration--;
        target.setHp(target.getHp() - stregth);
        if(target.getHp() <= 0) {
            target.die();
        }
    }

    @Override
    public boolean isOver() {
        return duration == 0;
    }


}
