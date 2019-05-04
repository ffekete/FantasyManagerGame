package com.mygdx.game.logic;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.logic.activity.manager.ActivityManager;
import com.mygdx.game.logic.time.DayTimeCalculator;
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

    private MapRegistry mapRegistry = MapRegistry.INSTANCE;
    private DayTimeCalculator dayTimeCalculator = DayTimeCalculator.INSTANCE;

    public GameLogicController(ActorRegistry actorRegistry) {
        this.actorRegistry = actorRegistry;
        this.activityManager = new ActivityManager();
    }

    public void update() {
        long start = System.currentTimeMillis();
        counter += Gdx.graphics.getDeltaTime();
        if(counter > 0.025) {
            counter = 0;

            dayTimeCalculator.update();

            calculateVisibilityForMaps();
            MapRegistry.INSTANCE.getMaps().forEach(map -> {
                if(actorRegistry.containsAnyHeroes(map)) {
                    actorRegistry.getActors(map).forEach(actor -> {
                        actor.increaseHunger(1);
                        activityManager.manage(actor);
                        actor.getActivityStack().performNext();
                    });
                }
            });
        }
        if(Config.SHOW_ELAPSED_TIME)
            System.out.println(System.currentTimeMillis() - start);
    }

    private void calculateVisibilityForMaps() {
        for(Map2D map : mapRegistry.getMaps()) {
            if(actorRegistry.containsAnyHeroes(map)) {
                List<Actor> coordinatesForVisibilityCalculation = new ArrayList<>();
                // get all actors in the list
                coordinatesForVisibilityCalculation.addAll(actorRegistry.getActors(map));

                VisibilityCalculator visibilityCalculator = map.getVisibilityCalculator();
                // generate visible areas for all the actors
                VisibilityMask visibilityMask = visibilityCalculator.generateMask(map, 15, coordinatesForVisibilityCalculation);
                VisibilityMapRegistry.INSTANCE.add(map, visibilityMask);
            }
        }
    }
}
