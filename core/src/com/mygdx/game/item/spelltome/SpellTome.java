package com.mygdx.game.item.spelltome;

import com.mygdx.game.spell.Spell;

import java.util.HashSet;
import java.util.Set;

public class SpellTome {

    Set<Spell> spells;

    public SpellTome() {
        spells = new HashSet<>();
    }

    public void add(Spell spell) {
        spells.add(spell);
    }

    public Set<Spell> getSpells() {
        return spells;
    }

    public void clear() {
        spells.clear();
    }
}
