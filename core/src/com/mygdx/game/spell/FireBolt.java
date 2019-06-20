package com.mygdx.game.spell;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.action.Action;
import com.mygdx.game.logic.action.FireboltAction;
import com.mygdx.game.logic.action.SmallExplosionAction;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.ActionRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FireBolt implements Spell, FireSpell, DestructiveSpell {

    private Point start;
    private Point end;
    private List<Point> path;
    private Action flameAction;
    private ProjectilePathCalculator projectilePathCalculator = new ProjectilePathCalculator();
    private Map2D map;
    private Actor caster;
    private Actor target;

    public FireBolt() {
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
        if (!path.isEmpty())
            flameAction.setCoordinates(path.remove(0));
    }

    @Override
    public void finish() {
        ActionRegistry.INSTANCE.add(map, new SmallExplosionAction(end.getX(), end.getY()));
        new FireDamage(caster).calculate(target, new Random().nextInt(3) + 6);
        caster.setMana(caster.getMana() - Config.Spell.FIREBOLT_MANA_COST);
    }

    @Override
    public boolean isFinished() {
        return path.isEmpty();
    }

    @Override
    public int getManaCost() {
        return Config.Spell.FIREBOLT_MANA_COST;
    }

    @Override
    public int getArea() {
        return 0;
    }

    private void calculatePath() {
        path = new ArrayList<>();
        projectilePathCalculator.calculate(start.getX(), start.getY(), end.getX(), end.getY(), path);
    }


}
