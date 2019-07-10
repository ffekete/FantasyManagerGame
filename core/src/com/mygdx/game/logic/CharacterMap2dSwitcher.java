package com.mygdx.game.logic;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.interactive.DungeonEntrance;
import com.mygdx.game.object.interactive.Ladder;
import com.mygdx.game.object.light.LightSource;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.LightSourceRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.ObjectRegistry;
import com.mygdx.game.renderer.camera.CameraPositionController;

public class CharacterMap2dSwitcher {

    public static final CharacterMap2dSwitcher INSTANCE = new CharacterMap2dSwitcher();

    private final ObjectRegistry objectRegistry = ObjectRegistry.INSTANCE;
    private final ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    private final CameraPositionController cameraPositionController = CameraPositionController.INSTANCE;
    private final MapRegistry mapRegistry = MapRegistry.INSTANCE;

    public void switchTo(Map2D to, Map2D from, Actor actor) {
        actorRegistry.remove(from, actor);
        actor.setCurrentMap(to);

        Ladder entrance = (Ladder) objectRegistry.getObject(to, Ladder.class).get().get(0);
        Point enter = entrance.getCoordinates();

        if(!actor.getCurrentMap().getTile(enter.getX() + 1, enter.getY()).isObstacle() &&
                !actor.getCurrentMap().getTile(enter.getX() + 1, enter.getY()).isObstacle()) {
            actor.setCoordinates(Point.of(enter.getX() + 1, enter.getY()));

        } else if(!actor.getCurrentMap().getTile(enter.getX() - 1, enter.getY()).isObstacle() &&
                !actor.getCurrentMap().getTile(enter.getX() - 1, enter.getY()).isObstacle()) {
            actor.setCoordinates(Point.of(enter.getX() - 1, enter.getY()));

        } else if(!actor.getCurrentMap().getTile(enter.getX(), enter.getY() + 1).isObstacle() &&
                !actor.getCurrentMap().getTile(enter.getX(), enter.getY() + 1).isObstacle()) {
            actor.setCoordinates(Point.of(enter.getX() , enter.getY() + 1));

        } else if(!actor.getCurrentMap().getTile(enter.getX() , enter.getY() - 1).isObstacle() &&
                !actor.getCurrentMap().getTile(enter.getX(), enter.getY() - 1).isObstacle()) {
            actor.setCoordinates(Point.of(enter.getX(), enter.getY() - 1));
        } else {
            throw new RuntimeException("Entry area is surrounded");
        }


        actorRegistry.add(to, actor);

        if(cameraPositionController.getFocusedOn() == actor) {
            mapRegistry.setCurrentMapToShow(to);
        }
    }

    private CharacterMap2dSwitcher() {
    }
}
