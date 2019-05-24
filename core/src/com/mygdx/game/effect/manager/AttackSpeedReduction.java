package com.mygdx.game.effect.manager;

import com.mygdx.game.effect.Effect;

public class AttackSpeedReduction implements Effect {

    private final int strength;

    public AttackSpeedReduction(int strength) {
        this.strength = strength;
    }

    @Override
    public void update() {
    }

    @Override
    public boolean isOver() {
        return false;
    }

    @Override
    public int getPower() {
        return strength;
    }
}
