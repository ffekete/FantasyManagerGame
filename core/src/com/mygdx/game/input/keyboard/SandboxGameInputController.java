package com.mygdx.game.input.keyboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.logic.GameState;
import com.mygdx.game.logic.controller.GameFlowControllerFacade;
import com.mygdx.game.logic.controller.InventoryGameLogicController;
import com.mygdx.game.logic.controller.SandboxGameLogicController;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.renderer.camera.CameraPositionController;

import java.util.Optional;

public class SandboxGameInputController {

    public static final SandboxGameInputController INSTANCE = new SandboxGameInputController();

    private final CameraPositionController cameraPositionController = CameraPositionController.INSTANCE;

    public boolean handleKeyboardInput(int keycode, Camera camera) {
        float delta = Gdx.graphics.getRawDeltaTime();

        if(keycode == Input.Keys.B) {
            GameFlowControllerFacade.INSTANCE.setGameState(GameState.Builder);
        }

        if(keycode == Input.Keys.I && CameraPositionController.INSTANCE.getFocusedOn() != null) {
            InventoryGameLogicController.INSTANCE.setActor(cameraPositionController.getFocusedOn());
            GameFlowControllerFacade.INSTANCE.setGameState(GameState.Inventory);
        }

        if(keycode == Input.Keys.N) {
            if(CameraPositionController.INSTANCE.isFocusedOn()) {
                Optional<Actor> nextActor = ActorRegistry.INSTANCE.getNext(MapRegistry.INSTANCE.getCurrentMapToShow());
                nextActor.ifPresent(CameraPositionController.INSTANCE::focusOn);
            }
        }

        if (keycode == Input.Keys.M) {
            if(!CameraPositionController.INSTANCE.isFocusedOn()) {
                MapRegistry.INSTANCE.setCurrentMapToShow(MapRegistry.INSTANCE.getNext());
                cameraPositionController.update();
            }
        }

        if (keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
            System.exit(0);
        }

        if(keycode == Input.Keys.SPACE) {
            SandboxGameLogicController.INSTANCE.togglePause();
        }

        if (keycode == Input.Keys.LEFT) {
            //camera.position.x -= 20.0 * delta;
            cameraPositionController.offset(-20.0f * delta, 0f);
        }
        if (keycode == Input.Keys.RIGHT) {
            //camera.position.x += 20.0 * delta;
            cameraPositionController.offset(20.0f * delta, 0f);
        }
        if (keycode == Input.Keys.DOWN) {
            //camera.position.y -= 20.0 * delta;
            cameraPositionController.offset( 0f, -20.0f * delta);
        }
        if (keycode == Input.Keys.UP) {
            //camera.position.y += 20.0 * delta;
            cameraPositionController.offset(0f, 20.0f * delta);
        }

        if (keycode == Input.Keys.F) {
            ActorRegistry.INSTANCE.getActors(MapRegistry.INSTANCE.getCurrentMapToShow())
                    .stream()
                    .filter(actor -> actor.getAlignment().equals(Alignment.FRIENDLY))
                    .findFirst().ifPresent(CameraPositionController.INSTANCE::focusOn);

        }
        camera.update();

        return true;
    }

}
