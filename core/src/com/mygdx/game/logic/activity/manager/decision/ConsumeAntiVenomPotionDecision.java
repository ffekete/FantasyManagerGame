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
        if (actor.isSleeping()) {
            return false;
        }

        if (actor.getActivityStack().contains(ConsumeAntiVenomPotion.class)) {
            return true;
        }

        if (effectRegistry.getAll(actor).stream().anyMatch(effect -> Poison.class.isAssignableFrom(effect.getClass()))) {
            if (actor.getInventory().has(AntiVenomPotion.class)) {
                actor.getActivityStack().reset();
                Activity activity = new ConsumeAntiVenomPotion(actor, actor.getInventory().get(AntiVenomPotion.class));
                actor.getActivityStack().add(activity);
                return true;
            }
        }
        return false;
    }
}
