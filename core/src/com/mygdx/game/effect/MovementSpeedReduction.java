package com.mygdx.game.effect;

public class MovementSpeedReduction implements Effect, DebuffEffect {

    private final int strength;

    public MovementSpeedReduction(int strength) {
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
