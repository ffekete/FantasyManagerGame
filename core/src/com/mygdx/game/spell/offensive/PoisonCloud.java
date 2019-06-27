package com.mygdx.game.spell.offensive;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.component.skill.MagicSkill;
import com.mygdx.game.logic.selector.AreaBasedEnemiesSelector;
import com.mygdx.game.logic.selector.SelectionUtils;
import com.mygdx.game.effect.Poison;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.action.PoisonCloudAction;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.light.LightSourceType;
import com.mygdx.game.object.light.TimedLightSource;
import com.mygdx.game.registry.ActionRegistry;
import com.mygdx.game.registry.EffectRegistry;
import com.mygdx.game.registry.LightSourceRegistry;
import com.mygdx.game.spell.*;

import java.util.ArrayList;
import java.util.List;

public class PoisonCloud implements Spell, DarkSpell, OffensiveSpell {

    private final AreaBasedEnemiesSelector areaBasedEnemiesSelector = new AreaBasedEnemiesSelector();

    private Point end;
    private List<Point> path;
    private Map2D map;
    private Actor caster;
    private Actor target;

    public PoisonCloud() {
        path = new ArrayList<>();
    }

    @Override
    public void init(Actor caster, Actor target) {
        end = target.getCoordinates();
        map = caster.getCurrentMap();
        this.caster = caster;
        this.target = target;
    }

    @Override
    public void update() {

    }

    @Override
    public void finish() {
        ActionRegistry.INSTANCE.add(map, new PoisonCloudAction(end.getX(), end.getY()));

        caster.setMana(caster.getMana() - Config.Spell.POISON_CLOUD_MANA_COST);

        for(Actor actor: areaBasedEnemiesSelector.findAllEnemiesWithinRange(target.getCoordinates(), map,  Config.Spell.POISON_CLOUD_RANGE)) {
            EffectRegistry.INSTANCE.add(new Poison(actor.getMagicSkills().get(MagicSkill.DarkMagic) + 1, 20, actor, caster), actor);
        }

        LightSourceRegistry.INSTANCE.add(map, new TimedLightSource(target.getX(), target.getY(), Color.GREEN, 15f, LightSourceType.Beam, 10));
    }

    @Override
    public boolean isFinished() {
        return path.isEmpty();
    }

    @Override
    public int getManaCost() {
        return Config.Spell.POISON_CLOUD_MANA_COST;
    }

    @Override
    public int getArea() {
        return Config.Spell.POISON_CLOUD_RANGE;
    }

    @Override
    public int getStrength() {
        return Config.Spell.POISON_CLOUD_STRENGTH;
    }
}
