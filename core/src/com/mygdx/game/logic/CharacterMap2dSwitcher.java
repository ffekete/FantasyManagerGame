package com.mygdx.game.logic;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.renderer.camera.CameraPositionController;

public class CharacterMap2dSwitcher {

    public static final CharacterMap2dSwitcher INSTANCE = new CharacterMap2dSwitcher();

    private final ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    private final CameraPositionController cameraPositionController = CameraPositionController.INSTANCE;
    private final MapRegistry mapRegistry = MapRegistry.INSTANCE;

    public void switchTo(Map2D to, Map2D from, Actor actor) {
        actorRegistry.remove(from, actor);
        actor.setCurrentMap(to);
        actor.setCoordinates(to.getDefaultSpawnPoint());
        actorRegistry.add(to, actor);
        if(cameraPositionController.getFocusedOn() == actor) {
            mapRegistry.setCurrentMapToShow(to);
        }
    }

    private CharacterMap2dSwitcher() {
    }
}
