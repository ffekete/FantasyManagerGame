package com.mygdx.game.registry;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.effect.Effect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class EffectRegistry {

    public static final EffectRegistry INSTANCE = new EffectRegistry();

    private final Map<Actor, List<Effect>> effects = new HashMap<>();

    private EffectRegistry() {
    }

    public void add(Effect effect, Actor actor) {
        effects.computeIfAbsent(actor, value -> new CopyOnWriteArrayList<>());
        effects.get(actor).add(effect);
    }

    public void addAll(List<Effect> effectsList, Actor actor) {
        effects.computeIfAbsent(actor, value -> new CopyOnWriteArrayList<>());
        effects.get(actor).addAll(effectsList);
    }

    public Optional<Effect> hasEffect(Actor actor, Class<? extends Effect> effectClass) {
        if(effects.containsKey(actor)) {
            return (effects.get(actor).stream().filter(effect -> effectClass.isAssignableFrom(effect.getClass())).findAny());
        }
        return Optional.empty();
    }

    public List<Effect> getAll(Actor actor) {
        effects.computeIfAbsent(actor, vlue -> new CopyOnWriteArrayList<>());
        return effects.get(actor);
    }

    public void remove(Effect effect, Actor target) {
        if(effects.containsKey(target))
            effects.get(target).remove(effect);
    }
    public void removeAll(Actor actor) {
        effects.remove(actor);
    }
}
