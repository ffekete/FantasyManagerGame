package com.mygdx.game.logic.activity.single;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Equipable;
import com.mygdx.game.logic.activity.Activity;

public class EquipActivity implements Activity {

    private Actor actor;
    private Equipable equipable;

    public EquipActivity(Actor actor, Equipable equipable) {
        this.actor = actor;
        this.equipable = equipable;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public void update() {

    }

    @Override
    public void init() {

    }

    @Override
    public void cancel() {

    }

    @Override
    public int getPriority() {
        return Config.Activity.EQUIP_PRIORITY;
    }

    @Override
    public boolean isFirstRun() {
        return false;
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
        actor.equip(equipable);
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
    public void countDown() {

    }

    @Override
    public boolean isTriggered() {
        return false;
    }

    @Override
    public int compareTo(Activity o) {
        return Integer.compare(getPriority(), o.getPriority());
    }
}
