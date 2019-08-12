package com.mygdx.game.input.mouse.processor;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Item;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.action.Action;
import com.mygdx.game.logic.action.TargetMarkerAction;
import com.mygdx.game.logic.command.CutDownCommand;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.object.Cuttable;
import com.mygdx.game.object.Targetable;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.registry.*;
import com.mygdx.game.renderer.camera.CameraPositionController;

import java.util.*;
import java.util.stream.Collectors;

public class SandboxMouseActionProcessor {

    public static final SandboxMouseActionProcessor INSTANCE = new SandboxMouseActionProcessor();

    private final ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    private final ObjectRegistry objectRegistry = ObjectRegistry.INSTANCE;
    private final MapRegistry mapRegistry = MapRegistry.INSTANCE;
    private final ItemRegistry itemRegistry = ItemRegistry.INSTANCE;

    private Optional<WorldObject> worldObject;

    public boolean onClick(Point realWorldCoord, int pointer) {
        List<Actor> actors = getCharactersOnCell(realWorldCoord);
        if (!actors.isEmpty()) {
            CameraPositionController.INSTANCE.focusOn(actors.get(0));
            return true;
        }

        worldObject = getObjectOnCell(realWorldCoord);
        if(worldObject.isPresent()) {
            if(Targetable.class.isAssignableFrom(worldObject.get().getClass())) {
                Action action = new TargetMarkerAction((Targetable) worldObject.get());
                action.setCoordinates(realWorldCoord);
                ActionRegistry.INSTANCE.add(mapRegistry.getCurrentMapToShow(), action);
                CommandRegistry.INSTANCE.add(new CutDownCommand((Cuttable) worldObject.get()));
                return true;
            }
        }

        return false;
    }

    private List<Actor> getCharactersOnCell(Point worldCoord) {
        return actorRegistry.getActors(mapRegistry.getCurrentMapToShow()).stream()
                //.filter(actor -> actor.getAlignment().equals(Alignment.FRIENDLY))
                .filter((actor -> actor.getX() == worldCoord.getX() && actor.getY() == worldCoord.getY()))
                .collect(Collectors.toList());
    }

    private List<Item> getItemsOn(Point worldCoord) {
        return itemRegistry.getAllItems(mapRegistry.getCurrentMapToShow()).stream()
                .filter((item -> item.getX() == worldCoord.getX() && item.getY() == worldCoord.getY()))
                .collect(Collectors.toList());
    }

    private Optional<WorldObject> getObjectOnCell(Point worldCoord) {
        Optional<Set<WorldObject>> objects = objectRegistry.getObjects(mapRegistry.getCurrentMapToShow(), Cluster.of(worldCoord.getX(), worldCoord.getY()));

        if(objects.isPresent() && !objects.get().isEmpty()) {
            return objects.get().stream().filter(o -> o.getX() == worldCoord.getX() && o.getY() == worldCoord.getY())
                    .findFirst();
        }
        return Optional.empty();
    }

}
