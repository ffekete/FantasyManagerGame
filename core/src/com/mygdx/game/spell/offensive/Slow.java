package com.mygdx.game.spell.offensive;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.effect.MovementSpeedReduction;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.action.Action;
import com.mygdx.game.logic.action.FireboltAction;
import com.mygdx.game.logic.action.SlowAction;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.ActionRegistry;
import com.mygdx.game.registry.EffectRegistry;
import com.mygdx.game.spell.DebuffSpell;
import com.mygdx.game.spell.EarthSpell;
import com.mygdx.game.spell.OffensiveSpell;
import com.mygdx.game.spell.Spell;

public class Slow implements OffensiveSpell, Spell, EarthSpell, DebuffSpell {

    private Map2D map;
    private Actor caster;
    private Actor target;

    public Slow() {

    }

    @Override
    public void init(Actor caster, Actor target) {
        map = caster.getCurrentMap();
        this.caster = caster;
        this.target = target;
    }

    @Override
    public void update() {

    }

    @Override
    public void finish() {
        ActionRegistry.INSTANCE.add(map, new SlowAction(target.getCoordinates()));

        caster.setMana(caster.getMana() - Config.Spell.SLOW_MANA_COST);

        EffectRegistry.INSTANCE.add(new MovementSpeedReduction(-140), target);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public int getManaCost() {
        return Config.Spell.SLOW_MANA_COST;
    }

    @Override
    public int getArea() {
        return 0;
    }

    @Override
    public int getStrength() {
        return Config.Spell.SLOW_STRENGTH;
    }
}
