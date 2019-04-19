package com.mygdx.game.logic;

import com.mygdx.game.registry.ActorRegistry;

public class GameLogicController {

    private final ActorRegistry actorRegistry;

    public GameLogicController(ActorRegistry actorRegistry) {
        this.actorRegistry = actorRegistry;
    }

    public void update() {
        actorRegistry.getActors().forEach(actor -> {
            actor.getActivityStack().performNext();
        });
    }
}
