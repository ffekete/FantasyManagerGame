package com.mygdx.game.logic.activity.single;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.potion.ManaPotion;
import com.mygdx.game.logic.activity.Activity;

public class ConsumeManaPotion implements Activity {

    private final Actor actor;
    private final ManaPotion manaPotion;
    private int counter = 0;
    private int speed;
    private boolean firstRun = true;

    public ConsumeManaPotion(Actor actor, ManaPotion manaPotion) {
        this.actor = actor;
        this.manaPotion = manaPotion;
    }

    @Override
    public boolean isDone() {
        return counter == speed;
    }

    @Override
    public void update() {

    }

    @Override
    public void init() {
        speed = actor.getMovementSpeed();
        firstRun = false;
    }

    @Override
    public void cancel() {

    }

    @Override
    public int getPriority() {
        return Config.Activity.MANA_POTION_CONSUME_PRIORITY;
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
        manaPotion.consume(actor);
        actor.getInventory().remove(manaPotion);
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
        counter++;
    }

    @Override
    public boolean isTriggered() {
        return counter == speed;
    }

    @Override
    public int compareTo(Activity o) {
        return Integer.compare(getPriority(), o.getPriority());
    }
}