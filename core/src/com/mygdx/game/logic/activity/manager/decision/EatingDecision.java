package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.food.Food;
import com.mygdx.game.logic.activity.single.SimpleEatingActivity;

public class EatingDecision implements Decision {
    @Override
    public boolean decide(Actor actor) {
        if(actor.isHungry() && !actor.getActivityStack().contains(SimpleEatingActivity.class)) {
            if(actor.getInventory().has(Food.class)) {
                SimpleEatingActivity simpleEatingActivity = new SimpleEatingActivity(actor);
                actor.getActivityStack().suspendAll();
                actor.getActivityStack().add(simpleEatingActivity);
                return true;
            }
        }
        return false;
    }
}