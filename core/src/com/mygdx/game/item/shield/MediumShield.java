package com.mygdx.game.item.shield;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.effect.Effect;
import com.mygdx.game.effect.manager.AttackSpeedReduction;
import com.mygdx.game.item.category.Tier1;
import com.mygdx.game.logic.Point;
import com.mygdx.game.registry.EffectRegistry;

public class MediumShield implements Shield, Tier1 {

    private Effect attackSpeedReduction = new AttackSpeedReduction(-5);
    private EffectRegistry effectRegistry = EffectRegistry.INSTANCE;
    private Point coordinates = new Point(0,0);


    @Override
    public int getDefense() {
        return 6;
    }

    @Override
    public void onEquip(Actor actor) {
        effectRegistry.add(attackSpeedReduction, actor);
    }

    @Override
    public void onRemove(Actor actor) {
        effectRegistry.remove(attackSpeedReduction, actor);
    }

    @Override
    public int getPower() {
        return Config.Item.MEDIUM_SHIELD_POWER;
    }

    @Override
    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public int getX() {
        return this.coordinates.getX();
    }

    @Override
    public int getY() {
        return this.coordinates.getY();
    }
}
