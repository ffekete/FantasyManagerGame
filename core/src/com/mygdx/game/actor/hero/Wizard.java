package com.mygdx.game.actor.hero;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.item.spelltome.SpellTome;
import com.mygdx.game.spell.offensive.FireBall;
import com.mygdx.game.spell.offensive.FireBolt;
import com.mygdx.game.spell.offensive.Slow;

import static com.mygdx.game.faction.Alignment.FRIENDLY;

public class Wizard extends AbstractActor {

    public Wizard() {
        setAlignment(FRIENDLY);

        SpellTome spellTome = new SpellTome();
        spellTome.add(new FireBall());
        spellTome.add(new FireBolt());
        spellTome.add(new Slow());

        this.setSpellTome(spellTome);
    }
}
