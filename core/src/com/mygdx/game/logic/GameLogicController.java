package com.mygdx.game.logic;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.logic.activity.manager.ActivityManager;
import com.mygdx.game.registry.ActorRegistry;

public class GameLogicController {

    private double counter = 0.0;
    private final ActorRegistry actorRegistry;
    private final ActivityManager activityManager;

    public GameLogicController(ActorRegistry actorRegistry) {
        this.actorRegistry = actorRegistry;
        this.activityManager = new ActivityManager();
    }

    public void update() {
        counter += Gdx.graphics.getDeltaTime();
        if(counter > 0.05) {
            counter = 0;
            actorRegistry.getActors().forEach(actor -> {
                actor.increaseHunger(1);
                activityManager.manage(actor);
                actor.getActivityStack().performNext();
            });
        }
    }
}
