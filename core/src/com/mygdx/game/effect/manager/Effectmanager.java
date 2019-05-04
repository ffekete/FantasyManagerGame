package com.mygdx.game.effect.manager;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.effect.Effect;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.EffectRegistry;

import java.util.stream.Stream;

public class Effectmanager {

    public static final Effectmanager INSTANCE = new Effectmanager();

    private ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    private final EffectRegistry effectRegistry = EffectRegistry.INSTANCE;


    public void update() {
        for (Actor actor : actorRegistry.getAllActors()) {
            for (Effect effect : effectRegistry.getAll(actor)) {
                effect.update();
                if (effect.isOver()) {
                    effectRegistry.remove(effect, actor);
                }
            }
        }
    }

    private Effectmanager() {
    }
}
