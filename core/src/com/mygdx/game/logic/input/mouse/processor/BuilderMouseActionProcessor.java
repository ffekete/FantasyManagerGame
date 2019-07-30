package com.mygdx.game.logic.input.mouse.processor;

import com.mygdx.game.builder.BuilderTool;
import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.factory.ObjectFactory;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.ItemRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.ObjectRegistry;

import java.util.Optional;
import java.util.Set;

public class BuilderMouseActionProcessor {

    public static final BuilderMouseActionProcessor INSTANCE = new BuilderMouseActionProcessor();

    private final BuilderTool builderTool = BuilderTool.INSTANCE;
    private final ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    private final ObjectRegistry objectRegistry = ObjectRegistry.INSTANCE;
    private final MapRegistry mapRegistry = MapRegistry.INSTANCE;
    private final ItemRegistry itemRegistry = ItemRegistry.INSTANCE;

    public boolean onClick(Point realWorldCoord) {
        if (noObjectsOnCell(realWorldCoord) && noCharactersOnCell(realWorldCoord) && noItemsOnCell(realWorldCoord)) {
            ObjectFactory.create(builderTool.getSelectedBlock(), mapRegistry.getCurrentMapToShow(), ObjectPlacement.FIXED.X(realWorldCoord.getX()).Y(realWorldCoord.getY()));
            return true;
        }

        return false;
    }

    private boolean noCharactersOnCell(Point worldCoord) {
        return actorRegistry.getActors(mapRegistry.getCurrentMapToShow()).stream()
                .noneMatch((actor -> actor.getX() == worldCoord.getX() && actor.getY() == worldCoord.getY()));
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
