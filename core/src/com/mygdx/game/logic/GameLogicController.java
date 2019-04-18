package com.mygdx.game.logic;

import com.mygdx.game.registry.ActorRegistry;

public class GameLogicController {

    int counter = 0;

    private final ActorRegistry actorRegistry;

    public GameLogicController(ActorRegistry actorRegistry) {
        this.actorRegistry = actorRegistry;
    }

    public void update() {
        counter = (counter+1) % 20;

        if(counter == 0) {
            actorRegistry.getActors().forEach(actor -> {
                actor.getActivityStack().performNext();
            });
        }
    }
}
