package com.mygdx.game.actor.component.trait;

import java.util.HashSet;
import java.util.Set;

public class TraitList {

    private final Set<Trait> traits;

    public TraitList() {
        this.traits = new HashSet<>();
    }

    public boolean has(Trait trait) {
        return traits.contains(trait);
    }

    public void add(Trait trait) {
        traits.add(trait);
    }
}
