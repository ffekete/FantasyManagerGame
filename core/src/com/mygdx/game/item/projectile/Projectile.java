package com.mygdx.game.item.projectile;

import com.mygdx.game.actor.Actor;

public interface Projectile {

    void init(Actor caster, Actor target);
    void update();
    void finish();
    boolean isFinished();
}
