package com.mygdx.game.spell.offensive;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.effect.AttackSpeedReduction;
import com.mygdx.game.effect.MovementSpeedReduction;
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

        SlowAction slowAction = new SlowAction();

        slowAction.setCoordinates(target.getCoordinates());

        ActionRegistry.INSTANCE.add(map, slowAction);

        caster.setMana(caster.getMana() - Config.Spell.SLOW_MANA_COST);

        EffectRegistry.INSTANCE.add(new MovementSpeedReduction(Config.Spell.SLOW_MOVEMENT_SPEED_REDUCE_AMOUNT), target);
        EffectRegistry.INSTANCE.add(new AttackSpeedReduction(Config.Spell.SLOW_ATTACK_SPEED_REDUCE_AMOUNT), target);
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
