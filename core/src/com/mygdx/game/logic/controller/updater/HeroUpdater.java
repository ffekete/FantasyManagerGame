package com.mygdx.game.logic.controller.updater;

import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.MapRegistry;

public class HeroUpdater {

    public static final HeroUpdater INSTANCE = new HeroUpdater();

    private final ActorRegistry actorRegistry = ActorRegistry.INSTANCE;

    public void update() {
        MapRegistry.INSTANCE.getMaps().forEach(map -> {
            if (actorRegistry.containsAnyHeroes(map)) {

                actorRegistry.getActors(map).forEach(actor -> {
                    actor.increaseHunger(1);
                    actor.increaseSleepiness(1);
                    actor.increaseTrainingNeeds(1);
                    actor.getActivityStack().performNext();
                });
            }
        });
    }

}
