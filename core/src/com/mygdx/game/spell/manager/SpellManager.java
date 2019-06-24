package com.mygdx.game.spell.manager;

import com.mygdx.game.registry.SpellRegistry;
import com.mygdx.game.spell.Spell;

public class SpellManager {

    public static final SpellManager INSTANCE = new SpellManager();

    private final SpellRegistry spellRegistry = SpellRegistry.INSTANCE;

    // for performance tuning
    private Spell spell;

    private SpellManager() {
    }

    public void update() {

        for (int i = 0; i < spellRegistry.getSpells().size(); i++) {
            spell = spellRegistry.getSpells().get(i);
            if (!spell.isFinished()) {
                spell.update();
            } else {
                spell.finish();
                spellRegistry.remove(spell);
            }
        }
    }

}
