package com.mygdx.game.logic.selector;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.component.trait.Trait;
import com.mygdx.game.effect.DebuffEffect;
import com.mygdx.game.effect.Effect;
import com.mygdx.game.registry.EffectRegistry;
import com.mygdx.game.spell.OffensiveSpell;
import com.mygdx.game.spell.Spell;
import com.mygdx.game.spell.SpellEffectMapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum OffensiveSpellSelector implements Selector {

    Strongest {

        @Override
        public Optional<Spell> find(Actor actor, Actor target) {
            // find all the spells
            List<Spell> spells = actor.getSpellTome().getSpells()
                    .stream()
                    .filter(spell1 -> spell1.getManaCost() <= actor.getMana())
                    .filter(spell1 -> OffensiveSpell.class.isAssignableFrom(spell1.getClass()))
                    .collect(Collectors.toList());

            if(spells.isEmpty()) {
                return Optional.empty();
            }

            AreaBasedEnemiesSelector areaBasedEnemiesSelector = new AreaBasedEnemiesSelector();

            // select the one that does not damage friends... or select lunatic spells!
            List<Spell> viableSpells = actor.hasTrait(Trait.Lunatic) ? spells : getSafeAreaSpells(actor, target, spells, areaBasedEnemiesSelector);

            if(viableSpells.isEmpty())
                return Optional.empty();

            // filter debuffs already applied on enemy
            List<Spell> filteredSpells = new ArrayList<>();

            viableSpells = viableSpells.stream()
                    .filter(spell1 -> {
                        List<Effect> debuffEffects = EffectRegistry.INSTANCE.getAll(target).stream().filter(effect -> DebuffEffect.class.isAssignableFrom(effect.getClass())).collect(Collectors.toList());

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
            return spell;
        }
    };

    private static List<Spell> getSafeAreaSpells(Actor actor, Actor target, List<Spell> spells, AreaBasedEnemiesSelector areaBasedEnemiesSelector) {
        return spells.stream()
                        .filter(spell -> {
                            return areaBasedEnemiesSelector.findAllEnemiesWithinRange(target.getCoordinates(), actor.getCurrentMap(), spell.getArea())
                                    .stream().noneMatch(actor1 -> actor1.getAlignment().equals(actor.getAlignment()));
                        })
                        .collect(Collectors.toList());
    }

}
