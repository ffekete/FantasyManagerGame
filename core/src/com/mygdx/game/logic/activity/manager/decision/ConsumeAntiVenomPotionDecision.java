package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.effect.Poison;
import com.mygdx.game.item.potion.AntiVenomPotion;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.single.ConsumeAntiVenomPotion;
import com.mygdx.game.registry.EffectRegistry;

public class ConsumeAntiVenomPotionDecision implements Decision {

    private final EffectRegistry effectRegistry = EffectRegistry.INSTANCE;

    @Override
    public boolean decide(Actor actor) {
        if (actor.getActivityStack().contains(ConsumeAntiVenomPotionDecision.class)) {
            return true;
        }

        if (effectRegistry.getAll(actor).stream().anyMatch(effect -> Poison.class.isAssignableFrom(effect.getClass())) && !actor.getActivityStack().contains(ConsumeAntiVenomPotionDecision.class)) {
            if(actor.getInventory().has(AntiVenomPotion.class)) {
                actor.getActivityStack().clear();
                Activity activity = new ConsumeAntiVenomPotion(actor, actor.getInventory().get(AntiVenomPotion.class));
                actor.getActivityStack().add(activity);
                return true;
            }
        }
        return false;
    }
}