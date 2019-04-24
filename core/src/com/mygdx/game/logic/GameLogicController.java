package com.mygdx.game.logic;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.logic.activity.manager.ActivityManager;
import com.mygdx.game.logic.visibility.VisibilityCalculator;
import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;

import java.util.ArrayList;
import java.util.List;

public class GameLogicController {

    private double counter = 0.0;
    private final ActorRegistry actorRegistry;
    private final ActivityManager activityManager;
    private final VisibilityMapRegistry visibilityMapRegistry = VisibilityMapRegistry.INSTANCE;
    MapRegistry mapRegistry = MapRegistry.INSTANCE;

    public GameLogicController(ActorRegistry actorRegistry) {
        this.actorRegistry = actorRegistry;
        this.activityManager = new ActivityManager();
    }

    public void update() {
        counter += Gdx.graphics.getDeltaTime();
        if(counter > 0.05) {
            counter = 0;

            calculateVisibilityForMaps();

            actorRegistry.getActors().forEach(actor -> {
                actor.increaseHunger(1);
                activityManager.manage(actor);
                actor.getActivityStack().performNext();
            });
        }
    }

    private void calculateVisibilityForMaps() {
        for(Map2D map : mapRegistry.getMaps()) {
            List<Point> coordinatesForVisibilityCalculation = new ArrayList<>();
            actorRegistry.getActors().forEach(actor -> {
                        if (Alignment.FRIENDLY.equals(actor.getAlignment()))
                            coordinatesForVisibilityCalculation.add(new Point(actor.getX(), actor.getY()));
                    }
            );

            VisibilityCalculator visibilityCalculator = map.getVisibilityCalculator();
            VisibilityMask visibilityMask = visibilityCalculator.generateMask(map, 15, coordinatesForVisibilityCalculation);
            VisibilityMapRegistry.INSTANCE.add(map, visibilityMask);
        }
    }
}
