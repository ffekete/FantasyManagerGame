package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.potion.ManaPotion;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.single.ConsumeManaPotion;

public class ConsumeManapotionDecision implements Decision {

    @Override
    public boolean decide(Actor actor) {

        if(actor.isSleeping()) {
            return false;
        }

        if (actor.getActivityStack().getCurrent().getMainClass().equals(ConsumeManaPotion.class)) {
            return true;
        }

        if (actor.getInventory().has(ManaPotion.class)) {
            if (actor.getMana() < actor.getMaxMana() / Config.Actor.LOW_MANA_THRESHOLD_DIVIDER) {
                actor.getActivityStack().reset();
                Activity activity = new ConsumeManaPotion(actor, actor.getInventory().get(ManaPotion.class));
                actor.getActivityStack().add(activity);
                return true;
            }
        }
        return false;
    }
}
