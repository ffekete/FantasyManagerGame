package com.mygdx.game.logic.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.GameLogicController;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.renderer.camera.CameraPositionController;

import java.util.Optional;

public class GameInputController {

    public static final GameInputController INSTANCE = new GameInputController();

    private final CameraPositionController cameraPositionController = CameraPositionController.INSTANCE;



    public boolean handleKeyboardInput(int keycode, Camera camera, Actor selectedActor) {
        float delta = Gdx.graphics.getRawDeltaTime();

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
            GameLogicController.INSTANCE.togglePause();
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

        if (keycode == Input.Keys.F && selectedActor != null) {
            CameraPositionController.INSTANCE.focusOn(selectedActor);
        }
        camera.update();

        return true;
    }

}
