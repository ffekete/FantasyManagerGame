package com.mygdx.game.logic.activity.single;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.registry.SpellRegistry;
import com.mygdx.game.spell.Spell;

public class OffensiveSpellCastActivity implements Activity {

    private final SpellRegistry spellRegistry = SpellRegistry.INSTANCE;

    private final Spell spell;
    private final Actor caster;
    private final Actor target;
    private int counter = Config.Rules.SPELL_CAST_FREQUENCY;
    private boolean firstRun = true;


    public OffensiveSpellCastActivity(Spell spell, Actor caster, Actor target) {
        this.spell = spell;
        this.caster = caster;
        this.target = target;
    }

    @Override
    public boolean isDone() {
        return counter <= 0;
    }

    @Override
    public void update() {

    }

    @Override
    public void init() {
        spell.init(caster, target);
        spellRegistry.add(spell);
        firstRun = false;
    }

    @Override
    public void cancel() {

    }

    @Override
    public int getPriority() {
        return Config.Activity.OFFENSIVE_SPELL_CAST_PRIORITY;
    }

    @Override
    public boolean isFirstRun() {
        return firstRun;
    }

    @Override
    public void suspend() {

    }

    @Override
    public void resume() {

    }

    @Override
    public boolean isSuspended() {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean isCancellable() {
        return false;
    }

    @Override
    public Class getCurrentClass() {
        return this.getClass();
    }

    @Override
    public Activity getCurrentActivity() {
        return this;
    }

    @Override
    public void countDown() {
        counter--;
    }

    @Override
    public boolean isTriggered() {
        return counter == 0;
    }

    @Override
    public int compareTo(Activity o) {
        return Integer.compare(getPriority(), o.getPriority());
    }

    public Actor getTarget() {
        return target;
    }

    @Override
    public Class<? extends Activity> getMainClass() {
        return this.getClass();
    }
}
