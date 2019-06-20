package com.mygdx.game.spell;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.action.Action;
import com.mygdx.game.logic.action.ExplosionAction;
import com.mygdx.game.logic.action.FireboltAction;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.ActionRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FireBall implements Spell, FireSpell, DestructiveSpell {

    private Point start;
    private Point end;
    private List<Point> path;
    Action flameAction;
    ProjectilePathCalculator projectilePathCalculator = new ProjectilePathCalculator();
    private Map2D map;
    private Actor caster;

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
    }

    @Override
    public void update() {
        if(!path.isEmpty())
            flameAction.setCoordinates(path.remove(0));
    }

    @Override
    public void finish() {
        ActionRegistry.INSTANCE.add(map, new ExplosionAction(end.getX(), end.getY()));
        new FireAreaDamage(caster).calculate(end, map, Config.Spell.FIREBALL_RANGE, new Random().nextInt(5) + 12);
        caster.setMana(caster.getMana() - Config.Spell.FIREBALL_MANA_COST);
    }

    @Override
    public boolean isFinished() {
        return path.isEmpty();
    }

    @Override
    public int getManaCost() {
        return Config.Spell.FIREBALL_MANA_COST;
    }

    private void calculatePath() {
        path = new ArrayList<>();
        projectilePathCalculator.calculate(start.getX(), start.getY(), end.getX(), end.getY(), path);
    }


}
