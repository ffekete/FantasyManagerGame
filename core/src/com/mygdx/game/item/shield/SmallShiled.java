package com.mygdx.game.item.shield;

import com.mygdx.game.actor.Actor;

public class SmallShiled implements Shield {

    private int defense;
    private int x;
    private int y;

    @Override
    public void onEquip(Actor actor) {
        System.out.println("Shield equipped");
    }

    @Override
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getDefense() {
        return defense;
    }
}
