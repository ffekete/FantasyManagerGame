package com.mygdx.game.logic;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.object.light.LightSource;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.LightSourceRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.renderer.camera.CameraPositionController;

public class CharacterMap2dSwitcher {

    public static final CharacterMap2dSwitcher INSTANCE = new CharacterMap2dSwitcher();

    private final ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    private final CameraPositionController cameraPositionController = CameraPositionController.INSTANCE;
    private final MapRegistry mapRegistry = MapRegistry.INSTANCE;
    private final LightSourceRegistry lightSourceRegistry = LightSourceRegistry.INSTANCE;

    public void switchTo(Map2D to, Map2D from, Actor actor) {
        actorRegistry.remove(from, actor);
        actor.setCurrentMap(to);
        actor.setCoordinates(to.getDefaultSpawnPoint());
        actorRegistry.add(to, actor);
        LightSource lightSource = lightSourceRegistry.getFor(actor);

        lightSourceRegistry.remove(from, lightSource);
        lightSourceRegistry.add(to, lightSource);

        if(cameraPositionController.getFocusedOn() == actor) {
            mapRegistry.setCurrentMapToShow(to);
        }
    }

    private CharacterMap2dSwitcher() {
    }
}
