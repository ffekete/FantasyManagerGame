package com.mygdx.game.logic.controller;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.regenerator.RegeneratorImpl;
import com.mygdx.game.effect.manager.EffectManager;
import com.mygdx.game.item.projectile.manager.ProjectileManager;
import com.mygdx.game.logic.HousePopulator;
import com.mygdx.game.logic.activity.manager.ActivityManager;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.controller.updater.GrowthUpdater;
import com.mygdx.game.logic.controller.updater.HeroUpdater;
import com.mygdx.game.logic.time.DayTimeCalculator;
import com.mygdx.game.logic.visibility.VisibilityCalculator;
import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.music.MusicPlayer;
import com.mygdx.game.quest.QuestManager;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;
import com.mygdx.game.spell.manager.SpellManager;

import java.util.List;

public class SandboxGameLogicController implements Controller {

    public final static SandboxGameLogicController INSTANCE = new SandboxGameLogicController(ActorRegistry.INSTANCE);

    private double counter = 0.0;
    private final ActorRegistry actorRegistry;
    private final ActivityManager activityManager;
    private final EffectManager effectmanager;
    private final SpellManager spellManager;
    private final ProjectileManager projectileManager;

    private boolean pauseGame = true;

    private int bucketNr = 0;

    private MapRegistry mapRegistry = MapRegistry.INSTANCE;
    private DayTimeCalculator dayTimeCalculator = DayTimeCalculator.INSTANCE;

    public SandboxGameLogicController(ActorRegistry actorRegistry) {
        this.actorRegistry = actorRegistry;
        this.activityManager = ActivityManager.INSTANCE;
        effectmanager = EffectManager.INSTANCE;
        spellManager = SpellManager.INSTANCE;
        projectileManager = ProjectileManager.INSTANCE;
    }

    @Override
    public void update() {
        long start = System.currentTimeMillis();

        if (pauseGame) {
            return;
        }

        counter += Gdx.graphics.getRawDeltaTime();

        if (counter > 0.025) {
            counter = 0;

            dayTimeCalculator.update();

            GrowthUpdater.INSTANCE.update();

            calculateVisibilityForMaps();

            MusicPlayer.INSTANCE.choose();

            long s = System.currentTimeMillis();
            MapRegistry.INSTANCE.getMaps().forEach(map -> {
                if (actorRegistry.containsAnyHeroes(map)) {

                    actorRegistry.getBucketedActors(map, bucketNr).forEach(actor -> {
                        activityManager.manage(actor);
                    });
                }
            });

            long i = System.currentTimeMillis();

            QuestManager.INSTANCE.update();
            HeroUpdater.INSTANCE.update();

            if(bucketNr >= Config.Engine.BUCKET_SIZE -1) {
                HousePopulator.INSTANCE.update();
            }

            bucketNr = (bucketNr + 1) % Config.Engine.BUCKET_SIZE;

            spellManager.update();
            projectileManager.update();
            effectmanager.update();

            for (RegeneratorImpl regenerator : RegeneratorImpl.values()) {
                regenerator.regenerateAll();
            }
        }
        if (Config.SHOW_ELAPSED_TIME && System.currentTimeMillis() - start > 0)
            System.out.println("Elapsed time in GameLogicUpdater " + (System.currentTimeMillis() - start));
    }

    private void calculateVisibilityForMaps() {
        for (Map2D map : mapRegistry.getMaps()) {
            if (actorRegistry.containsAnyHeroes(map)) {

                List<Actor> coordinatesForVisibilityCalculation;
                // get all actors in the list
                coordinatesForVisibilityCalculation = actorRegistry.getActors(map);

                VisibilityCalculator visibilityCalculator = map.getVisibilityCalculator();
                // generate visible areas for all the actors
                VisibilityMask visibilityMask = visibilityCalculator.generateMask(map, coordinatesForVisibilityCalculation);
                VisibilityMapRegistry.INSTANCE.add(map, visibilityMask);
            }
        }
        ActorMovementHandler.INSTANCE.getChangedCoordList().clear();
    }

    @Override
    public void togglePause() {
        this.pauseGame = !this.pauseGame;
    }

    @Override
    public boolean isPaused() {
        return pauseGame;
    }
}
