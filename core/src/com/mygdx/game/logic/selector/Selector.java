package com.mygdx.game.logic.selector;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.spell.Spell;

import java.util.Optional;

public interface Selector {

    Optional<Spell> find(Actor actor, Actor target);

}
