package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.potion.HealingPotion;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.single.ConsumeHealingPotion;

public class ConsumeHealingpotionDecision implements Decision {

    @Override
    public boolean decide(Actor actor) {
        if (actor.isSleeping()) {
            return false;
        }

        if(actor.getActivityStack().contains(ConsumeHealingPotion.class)) {
            return true;
        }

        if (actor.getInventory().has(HealingPotion.class)) {
            if (actor.getHp() < actor.getMaxHp() / Config.Actor.LOW_HP_THRESHOLD_DIVIDER) {
                //actor.getActivityStack().reset();
                Activity activity = new ConsumeHealingPotion(actor, actor.getInventory().get(HealingPotion.class));
                actor.getActivityStack().add(activity);
                return true;
            }
        }
        return false;
    }
}
