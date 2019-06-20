package com.mygdx.game.registry;

import com.mygdx.game.spell.Spell;

import java.util.ArrayList;
import java.util.List;

public class SpellRegistry {

    public static final SpellRegistry INSTANCE = new SpellRegistry();

    private final List<Spell> spells;

    private SpellRegistry() {
        spells = new ArrayList<>();
    }

    public List<Spell> getSpells() {

        return spells;
    }

    public void add(Spell spell) {
        spells.add(spell);
    }

    public void remove(Spell spell) {
        spells.remove(spell);
    }
}
