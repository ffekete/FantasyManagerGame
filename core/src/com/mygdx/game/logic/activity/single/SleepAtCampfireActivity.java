package com.mygdx.game.logic.activity.single;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.object.decoration.CampFire;
import com.mygdx.game.object.factory.ObjectFactory;
import com.mygdx.game.registry.ObjectRegistry;

public class SleepAtCampfireActivity implements Activity {

    private Actor actor;
    private int counter = 0;
    private CampFire campFire;

    public SleepAtCampfireActivity(Actor actor, CampFire campFire) {
        this.actor = actor;
        this.campFire = campFire;
    }

    @Override
    public boolean isDone() {
        return actor.getSleepinessLevel() <= Config.Rules.BASE_SLEEPINESS_LEVEL;
    }

    @Override
    public void update() {
        actor.decreaseSleepiness(1000);
    }

    @Override
    public void init() {

    }

    @Override
    public void cancel() {

    }

    @Override
    public int getPriority() {
        return Config.CommonActivity.SLEEP_PRIORITY;
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
        campFire.freeUp(actor.getCoordinates());
        if(campFire.allFree())
            ObjectFactory.remove(actor.getCurrentMap(), campFire);
    }

    @Override
    public boolean isCancellable() {
        return false;
    }

    @Override
    public Class getCurrentClass() {
        return SleepAtCampfireActivity.class;
    }

    @Override
    public Activity getCurrentActivity() {
        return this;
    }

    @Override
    public void countDown() {
        counter = (counter + 1) % 20;
    }

    @Override
    public boolean isTriggered() {
        return counter == 19;
    }

    @Override
    public int compareTo(Activity o) {
        return Integer.compare(getPriority(), o.getPriority());
    }
}
