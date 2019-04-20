package com.mygdx.game.logic;

import com.mygdx.game.logic.activity.manager.ActivityManager;
import com.mygdx.game.registry.ActorRegistry;

public class GameLogicController {

    private final ActorRegistry actorRegistry;
    private final ActivityManager activityManager;

    public GameLogicController(ActorRegistry actorRegistry) {
        this.actorRegistry = actorRegistry;
        this.activityManager = new ActivityManager();
    }

    public void update() {
        actorRegistry.getActors().forEach(actor -> {
            actor.increaseHunger(1);
            activityManager.manage(actor);
            actor.getActivityStack().performNext();
        });
    }
}
