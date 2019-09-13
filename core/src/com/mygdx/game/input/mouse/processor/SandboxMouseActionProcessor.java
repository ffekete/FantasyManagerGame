package com.mygdx.game.input.mouse.processor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Item;
import com.mygdx.game.logic.GameState;
import com.mygdx.game.logic.Point;
import com.mygdx.game.menu.CraftingObjectPopupMenuBuilder;
import com.mygdx.game.menu.CuttablePopupMenuBuilder;
import com.mygdx.game.menu.EmptyAreaPopupMenuBuilder;
import com.mygdx.game.object.CraftingObject;
import com.mygdx.game.object.Targetable;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.interactive.DungeonEntrance;
import com.mygdx.game.quest.MoveToLocationQuest;
import com.mygdx.game.registry.*;
import com.mygdx.game.renderer.camera.CameraPositionController;
import com.mygdx.game.stage.StageConfigurer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SandboxMouseActionProcessor {

    public static final SandboxMouseActionProcessor INSTANCE = new SandboxMouseActionProcessor();

    private final ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    private final ObjectRegistry objectRegistry = ObjectRegistry.INSTANCE;
    private final MapRegistry mapRegistry = MapRegistry.INSTANCE;
    private final ItemRegistry itemRegistry = ItemRegistry.INSTANCE;


    private Optional<WorldObject> worldObject;

    public boolean onClick(Point mouseCoord, Point realWorldCoord, int pointer) {

        if (realWorldCoord.getX() < 0 || realWorldCoord.getY() < 0)
            return false;

        List<Actor> actors = getCharactersOnCell(realWorldCoord);
        if (!actors.isEmpty()) {
            CameraPositionController.INSTANCE.focusOn(actors.get(0));
            return true;
        } else {
            CameraPositionController.INSTANCE.removeFocus();
        }

        worldObject = getObjectOnCell(realWorldCoord);

        if (!worldObject.isPresent()) {

            if (!mapRegistry.getCurrentMapToShow().isObstacle(realWorldCoord.getX(), realWorldCoord.getY()) && !mapRegistry.getCurrentMapToShow().getTile(realWorldCoord.getX(), realWorldCoord.getY()).isObstacle()) {
                StageConfigurer.INSTANCE.getFor(GameState.Sandbox).addActor(EmptyAreaPopupMenuBuilder.INSTANCE.build(mouseCoord, realWorldCoord));
                return true;
            }
            return false;
        }

        if (DungeonEntrance.class.isAssignableFrom(worldObject.get().getClass())) {
            MapRegistry.INSTANCE.setCurrentMapToShow(((DungeonEntrance) worldObject.get()).getTo());
            CameraPositionController.INSTANCE.setCameraPosition(((DungeonEntrance) worldObject.get()).getTo().getDefaultSpawnPoint());
            return true;
        }

        if (Targetable.class.isAssignableFrom(worldObject.get().getClass())) {

            StageConfigurer.INSTANCE.getFor(GameState.Sandbox).addActor(CuttablePopupMenuBuilder.INSTANCE.build(worldObject.get(), mouseCoord, realWorldCoord));
            return true;
        }

        if (CraftingObject.class.isAssignableFrom(worldObject.get().getClass())) {
            StageConfigurer.INSTANCE.getFor(GameState.Sandbox).addActor(CraftingObjectPopupMenuBuilder.INSTANCE.build((CraftingObject) worldObject.get(), mouseCoord, realWorldCoord));
            return true;
        }

        if (!mapRegistry.getCurrentMapToShow().isObstacle(realWorldCoord.getX(), realWorldCoord.getY()) && !mapRegistry.getCurrentMapToShow().getTile(realWorldCoord.getX(), realWorldCoord.getY()).isObstacle()) {
            StageConfigurer.INSTANCE.getFor(GameState.Sandbox).addActor(EmptyAreaPopupMenuBuilder.INSTANCE.build(mouseCoord, realWorldCoord));
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

    private List<Item> getItemsOn(Point worldCoord) {
        return itemRegistry.getAllItems(mapRegistry.getCurrentMapToShow()).stream()
                .filter((item -> item.getX() == worldCoord.getX() && item.getY() == worldCoord.getY()))
                .collect(Collectors.toList());
    }

    private Optional<WorldObject> getObjectOnCell(Point worldCoord) {
        /*Optional<Set<WorldObject>> objects = objectRegistry.getObjects(mapRegistry.getCurrentMapToShow(), Cluster.of(worldCoord.getX(), worldCoord.getY()));

        if (objects.isPresent() && !objects.get().isEmpty()) {
            return objects.get().stream().filter(o -> o.getX() == worldCoord.getX() && o.getY() == worldCoord.getY())
                    .findFirst();
        }*/


        WorldObject object = ObjectRegistry.INSTANCE.getObjectGrid().get(MapRegistry.INSTANCE.getCurrentMapToShow())[worldCoord.getX()][worldCoord.getY()][1];

        if (object == null)
            object = ObjectRegistry.INSTANCE.getObjectGrid().get(MapRegistry.INSTANCE.getCurrentMapToShow())[worldCoord.getX()][worldCoord.getY()][0];

        return Optional.ofNullable(object);
    }

}
