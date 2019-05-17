package com.mygdx.game.logic.activity.single;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.food.Food;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.CooldownActivity;

public class SimpleEatingActivity implements Activity, CooldownActivity {

    private boolean firstRun = true;
    private int priority = Config.Activity.EAT_PRIORITY;
    private int counter = 0;

    private final Actor actor;
    private boolean suspended = false;

    public SimpleEatingActivity(Actor actor) {
        this.actor = actor;
    }

    @Override
    public void countDown() {

    }

    @Override
    public boolean isTriggered() {
        return true;
    }

    @Override
    public boolean isDone() {
        return actor.getInventory().has(Food.class);
    }

    @Override
    public void update() {

    }

    @Override
    public void init() {
        firstRun = false;
        actor.setxOffset(0);
        actor.setyOffset(0);
    }

    @Override
    public void cancel() {
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public boolean isFirstRun() {
        return firstRun;
    }

    @Override
    public void suspend() {
        suspended = true;
    }

    @Override
    public void resume() {
        suspended = false;
    }

    @Override
    public int compareTo(Activity o) {
        return Integer.compare(priority, o.getPriority());
    }

    @Override
    public boolean isSuspended() {
        return suspended;
    }

    @Override
    public void clear() {
        if(actor.getInventory().has(Food.class)) {
            Food food = actor.getInventory().get(Food.class);
            actor.decreaseHunger(food.getNutritionAmount());
            actor.getInventory().remove(food);
        }
    }

    @Override
    public boolean isCancellable() {
        // food is gone
        return !actor.getInventory().has(Food.class);
    }

    @Override
    public Class getCurrentClass() {
        return this.getClass();
    }

    @Override
    public Activity getCurrentActivity() {
        return this;
    }

}
