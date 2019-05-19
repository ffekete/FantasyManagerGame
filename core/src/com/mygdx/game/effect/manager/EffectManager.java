package com.mygdx.game.effect.manager;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.effect.Effect;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.EffectRegistry;

public class EffectManager {

    public static final EffectManager INSTANCE = new EffectManager();

    private ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    private final EffectRegistry effectRegistry = EffectRegistry.INSTANCE;


    // private for better performance
    private Actor actor;
    private Effect effect;

    public void update() {
        for(int i = 0; i < actorRegistry.getAllActors().size(); i++) {
            actor = actorRegistry.getAllActors().get(i);
            for (int j = 0; j < effectRegistry.getAll(actor).size(); j++) {
                effect = effectRegistry.getAll(actor).get(j);
                effect.update();
                if (effect.isOver()) {
                    effectRegistry.remove(effect, actor);
                }
            }
        }
    }

    private EffectManager() {
    }
}
