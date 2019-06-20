package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.common.SelectionUtils;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.single.OffensiveSpellCastActivity;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.spell.DestructiveSpell;
import com.mygdx.game.spell.Spell;

import java.util.List;
import java.util.stream.Collectors;

public class OffensiveSpellCastDecision implements Decision {

    private final ActorRegistry actorRegistry = ActorRegistry.INSTANCE;

    @Override
    public boolean decide(Actor actor) {

        if(actor.getActivityStack().contains(OffensiveSpellCastActivity.class)) {
            return true;
        }

        Actor enemy = SelectionUtils.findClosestEnemy(actor, actorRegistry.getActors(actor.getCurrentMap()), Config.ATTACK_DISTANCE);

        List<Spell> spells = actor.getSpellTome().getSpells()
                .stream()
                .filter(spell1 -> spell1.getManaCost() <= actor.getMana())
                .filter(spell1 -> DestructiveSpell.class.isAssignableFrom(spell1.getClass()))
                .collect(Collectors.toList());

        if (enemy != null && !spells.isEmpty()) {
            Activity activity = new OffensiveSpellCastActivity(spells.get(0), actor, enemy);
            actor.getActivityStack().add(activity);
            return true;
        }
        return false;
    }
}
