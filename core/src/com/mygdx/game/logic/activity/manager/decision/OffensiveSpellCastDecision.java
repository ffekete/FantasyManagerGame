package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.common.SelectionUtils;
import com.mygdx.game.effect.DebuffEffect;
import com.mygdx.game.effect.Effect;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.single.OffensiveSpellCastActivity;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.EffectRegistry;
import com.mygdx.game.spell.DebuffSpell;
import com.mygdx.game.spell.OffensiveSpell;
import com.mygdx.game.spell.Spell;
import com.mygdx.game.spell.SpellEffectMapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OffensiveSpellCastDecision implements Decision {

    private final ActorRegistry actorRegistry = ActorRegistry.INSTANCE;

    @Override
    public boolean decide(Actor actor) {

        if(actor.getActivityStack().contains(OffensiveSpellCastActivity.class)) {
            return true;
        }

        Actor enemy = SelectionUtils.findClosestEnemy(actor, actorRegistry.getActors(actor.getCurrentMap()), Config.ATTACK_DISTANCE);

        if (enemy != null) {

            Optional<Spell> spell = SpellSelector.Strongest.find(actor, enemy);

            if(!spell.isPresent())
                return false;

            Activity activity = new OffensiveSpellCastActivity(spell.get(), actor, enemy);
            actor.getActivityStack().add(activity);
            return true;
        }
        return false;
    }
}
