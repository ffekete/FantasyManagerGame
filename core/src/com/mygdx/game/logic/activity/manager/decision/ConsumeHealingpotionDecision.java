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

        if (actor.getInventory().has(HealingPotion.class) && !actor.getActivityStack().getCurrent().getMainClass().equals(ConsumeHealingPotion.class)) {
            if (actor.getHp() < actor.getMaxHp() / Config.Actor.LOW_HP_THRESHOLD_DIVIDER) {
                actor.getActivityStack().clear();
                Activity activity = new ConsumeHealingPotion(actor, actor.getInventory().get(HealingPotion.class));
                actor.getActivityStack().add(activity);
                return true;
            }
        } else if (actor.getActivityStack().contains(ConsumeHealingPotion.class)) {
            return true;
        }
        return false;
    }
}
