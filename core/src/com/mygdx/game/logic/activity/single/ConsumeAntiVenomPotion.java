package com.mygdx.game.logic.activity.single;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.potion.AntiVenomPotion;
import com.mygdx.game.item.potion.ManaPotion;
import com.mygdx.game.logic.activity.Activity;

public class ConsumeAntiVenomPotion implements Activity {

    private final Actor actor;
    private final AntiVenomPotion antiVenomPotion;
    private int counter = 0;
    private int speed;
    private boolean firstRun = true;

    public ConsumeAntiVenomPotion(Actor actor, AntiVenomPotion antiVenomPotion) {
        this.actor = actor;
        this.antiVenomPotion = antiVenomPotion;
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
        return Config.Activity.ANTIVENOM_POTION_CONSUME_PRIORITY;
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
        firstRun = true;
    }

    @Override
    public boolean isSuspended() {
        return false;
    }

    @Override
    public void clear() {
        antiVenomPotion.consume(actor);
        actor.getInventory().remove(antiVenomPotion);
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

    @Override
    public Class<? extends Activity> getMainClass() {
        return this.getClass();
    }
}
