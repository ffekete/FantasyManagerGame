package com.mygdx.game.spell.offensive;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.effect.FireDamage;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.action.Action;
import com.mygdx.game.logic.action.ExplosionAction;
import com.mygdx.game.logic.action.FireboltAction;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.light.LightSourceType;
import com.mygdx.game.object.light.TimedLightSource;
import com.mygdx.game.registry.ActionRegistry;
import com.mygdx.game.registry.EffectRegistry;
import com.mygdx.game.registry.LightSourceRegistry;
import com.mygdx.game.spell.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FireBall implements Spell, FireSpell, OffensiveSpell {

    private Point start;
    private Point end;
    private List<Point> path;
    Action flameAction;
    ProjectilePathCalculator projectilePathCalculator = new ProjectilePathCalculator();
    private Map2D map;
    private Actor caster;
    private Actor target;
    private int delay = 2;

    public FireBall() {
        path = new ArrayList<>();
    }

    @Override
    public void init(Actor caster, Actor target) {
        start = caster.getCoordinates();
        end = target.getCoordinates();
        calculatePath();
        flameAction = new FireboltAction(caster.getCoordinates(), target.getCoordinates(), caster.getCurrentMap());
        ActionRegistry.INSTANCE.add(caster.getCurrentMap(), flameAction);
        map = caster.getCurrentMap();
        this.caster = caster;
        this.target = target;
    }

    @Override
    public void update() {
        delay--;
        if(delay == 0 && !path.isEmpty()) {
            flameAction.setCoordinates(path.remove(0));
            delay = 2;
        }
    }

    @Override
    public void finish() {
        ActionRegistry.INSTANCE.add(map, new ExplosionAction(end.getX(), end.getY()));
        FireAreaDamage.INSTANCE.calculate(caster, end, map, Config.Spell.FIREBALL_RANGE, new Random().nextInt(5) + 12);
        caster.setMana(caster.getMana() - Config.Spell.FIREBALL_MANA_COST);
        EffectRegistry.INSTANCE.add(new FireDamage(1, 5, target, caster), target);
        LightSourceRegistry.INSTANCE.add(map, new TimedLightSource(target.getX(), target.getY(), Color.RED, 15f, LightSourceType.Beam, 10));
    }

    @Override
    public boolean isFinished() {
        return path.isEmpty();
    }

    @Override
    public int getManaCost() {
        return Config.Spell.FIREBALL_MANA_COST;
    }

    @Override
    public int getArea() {
        return Config.Spell.FIREBALL_RANGE;
    }

    @Override
    public int getStrength() {
        return Config.Spell.FIREBALL_STRENGTH;
    }

    private void calculatePath() {
        path = new ArrayList<>();
        projectilePathCalculator.calculate(start.getX(), start.getY(), end.getX(), end.getY(), path);
    }


}
