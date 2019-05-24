package com.mygdx.game.item.shield;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.effect.Effect;
import com.mygdx.game.effect.manager.AttackSpeedReduction;
import com.mygdx.game.logic.Point;
import com.mygdx.game.registry.EffectRegistry;

public class MediumShield implements Shield {

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
    public void setCoordinates(int x, int y) {
        this.coordinates.update(x,y);
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
