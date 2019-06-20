package com.mygdx.game.spell;

import com.mygdx.game.actor.Actor;

public interface Spell {

    void init(Actor caster, Actor target);
    void update();
    void finish();
    boolean isFinished();
    int getManaCost();
    int getArea();
}
