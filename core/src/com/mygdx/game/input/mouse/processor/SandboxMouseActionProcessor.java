package com.mygdx.game.input.mouse.processor;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.ItemRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.ObjectRegistry;
import com.mygdx.game.renderer.camera.CameraPositionController;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SandboxMouseActionProcessor {

    public static final SandboxMouseActionProcessor INSTANCE = new SandboxMouseActionProcessor();

    private final ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    private final ObjectRegistry objectRegistry = ObjectRegistry.INSTANCE;
    private final MapRegistry mapRegistry = MapRegistry.INSTANCE;
    private final ItemRegistry itemRegistry = ItemRegistry.INSTANCE;

    public boolean onClick(Point realWorldCoord, int pointer) {
        List<Actor> actors = getCharactersOnCell(realWorldCoord);
        if (!actors.isEmpty()) {
            CameraPositionController.INSTANCE.focusOn(actors.get(0));
            return true;
        }

        return false;
    }

    private List<Actor> getCharactersOnCell(Point worldCoord) {
        return actorRegistry.getActors(mapRegistry.getCurrentMapToShow()).stream()
                //.filter(actor -> actor.getAlignment().equals(Alignment.FRIENDLY))
                .filter((actor -> actor.getX() == worldCoord.getX() && actor.getY() == worldCoord.getY()))
                .collect(Collectors.toList());
    }

    private boolean noItemsOnCell(Point worldCoord) {
        return itemRegistry.getAllItems(mapRegistry.getCurrentMapToShow()).stream()
                .noneMatch((item -> item.getX() == worldCoord.getX() && item.getY() == worldCoord.getY()));
    }

    private boolean noObjectsOnCell(Point worldCoord) {
        Optional<Set<WorldObject>> objects = objectRegistry.getObjects(mapRegistry.getCurrentMapToShow(), Cluster.of(worldCoord.getX(), worldCoord.getY()));

        return objects.map(worldObjects -> worldObjects
                .stream()
                .noneMatch((object -> (int) object.getX() == worldCoord.getX() && (int) object.getY() == worldCoord.getY())))
                .orElse(true);
    }

}
