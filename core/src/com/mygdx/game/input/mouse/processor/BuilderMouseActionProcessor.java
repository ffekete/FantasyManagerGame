package com.mygdx.game.input.mouse.processor;

import com.mygdx.game.builder.BuilderTool;
import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.factory.ObjectFactory;
import com.mygdx.game.object.floor.Floor;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.object.wall.IncompleteWoodenWall;
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
    private final BuildingActionValidator buildingActionValidator = new BuildingActionValidator();

    public boolean onClick(Point realWorldCoord) {

        if (realWorldCoord.getX() < 0 || realWorldCoord.getY() < 0 || realWorldCoord.getX() >= mapRegistry.getCurrentMapToShow().getWidth() || realWorldCoord.getY() >= mapRegistry.getCurrentMapToShow().getHeight()) {
            return true;
        }

        if (noObjectsOnCell(realWorldCoord) && noCharactersOnCell(realWorldCoord) && noItemsOnCell(realWorldCoord) && buildingActionValidator.validate(mapRegistry.getCurrentMapToShow(), realWorldCoord.getX(), realWorldCoord.getY(), builderTool.getSelectedBlock())) {
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
        Class classToPlace = builderTool.getSelectedBlock();
        int index = Floor.class.isAssignableFrom(classToPlace) ? 0 : 1;

        return objectRegistry.getObjectGrid().get(mapRegistry.getCurrentMapToShow())[Math.max(worldCoord.getX(), 0)][Math.max(worldCoord.getY(), 0)][index] == null;
    }

}
