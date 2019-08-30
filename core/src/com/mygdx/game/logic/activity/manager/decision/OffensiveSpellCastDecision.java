package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.single.OffensiveSpellCastActivity;
import com.mygdx.game.logic.selector.ClosestEnemySelector;
import com.mygdx.game.logic.selector.OffensiveSpellSelector;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.spell.Spell;

import java.util.Optional;

public class OffensiveSpellCastDecision implements Decision {

    private final ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    private final ClosestEnemySelector closestEnemySelector = new ClosestEnemySelector();

    @Override
    public boolean decide(Actor actor) {

        if(actor.isSleeping()) {
            return false;
        }

        if(actor.getActivityStack().contains(OffensiveSpellCastActivity.class)) {
            return true;
        }

        Actor enemy = closestEnemySelector.find(actor, actorRegistry.getActors(actor.getCurrentMap()), Config.ATTACK_DISTANCE);

        if (enemy != null) {

            Optional<Spell> spell = OffensiveSpellSelector.Strongest.find(actor, enemy);

            if(!spell.isPresent())
                return false;

            Activity activity = new OffensiveSpellCastActivity(spell.get(), actor, enemy);
            actor.getActivityStack().add(activity);
            return true;
        }
        return false;
    }
}
