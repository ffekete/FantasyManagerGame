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

        // find all the spells
        List<Spell> spells = actor.getSpellTome().getSpells()
                .stream()
                .filter(spell1 -> spell1.getManaCost() <= actor.getMana())
                .filter(spell1 -> OffensiveSpell.class.isAssignableFrom(spell1.getClass()))
                .collect(Collectors.toList());



        if (enemy != null && !spells.isEmpty()) {

            // select the one that does not damage friends
            List<Spell> viableSpells = spells.stream()
                    .filter(spell -> {
                        return SelectionUtils.findAllEnemiesWithinRange(enemy.getCoordinates(), actor.getCurrentMap(), spell.getArea())
                                .stream().noneMatch(actor1 -> actor1.getAlignment().equals(actor.getAlignment()));
                    })
                    .collect(Collectors.toList());

            if(viableSpells.isEmpty())
                return false;

            // filter debuffs already applied on enemy
            List<Spell> filteredSpells = new ArrayList<>();

            viableSpells = viableSpells.stream()
                    .filter(spell1 -> {
                        List<Effect> debuffEffects = EffectRegistry.INSTANCE.getAll(enemy).stream().filter(effect -> DebuffEffect.class.isAssignableFrom(effect.getClass())).collect(Collectors.toList());

                        List<Class<? extends Effect>> spellEffects = SpellEffectMapper.INSTANCE.getFor(spell1.getClass());

                        for(Class<? extends Effect> e : spellEffects) {
                            if(DebuffEffect.class.isAssignableFrom(e) && debuffEffects.stream().anyMatch(effect -> effect.getClass().equals(e))) {
                                return false;
                            }
                        }

                        filteredSpells.add(spell1);
                        return true;

                    }).collect(Collectors.toList());

            // find the strongest spell
            Optional<Spell> spell = filteredSpells.stream()
                    .max(Comparator.comparing(Spell::getStrength));

            if(!spell.isPresent())
                return false;

            Activity activity = new OffensiveSpellCastActivity(spell.get(), actor, enemy);
            actor.getActivityStack().add(activity);
            return true;
        }
        return false;
    }
}
