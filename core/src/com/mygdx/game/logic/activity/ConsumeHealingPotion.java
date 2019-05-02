package com.mygdx.game.logic.activity;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.potion.HealingPotion;

public class ConsumeHealingPotion implements Activity {

    private final Actor actor;
    private final HealingPotion healingPotion;
    private int counter = 0;
    private int speed;
    private boolean firstRun = true;

    public ConsumeHealingPotion(Actor actor, HealingPotion healingPotion) {
        this.actor = actor;
        this.healingPotion = healingPotion;
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
        return Config.Activity.HEALING_POTION_CONSUME_PRIORITY;
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
        System.out.println(actor.getName() + ": gulp!");
        healingPotion.consume(actor);
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
