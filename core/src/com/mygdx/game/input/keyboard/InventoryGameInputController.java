package com.mygdx.game.input.keyboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.builder.BuilderTool;
import com.mygdx.game.logic.GameState;
import com.mygdx.game.logic.controller.GameFlowControllerFacade;
import com.mygdx.game.object.wall.IncompleteWoodenDoorWall;
import com.mygdx.game.object.wall.IncompleteWoodenWall;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.renderer.camera.CameraPositionController;
import com.mygdx.game.renderer.inventory.InventoryRenderer;

public class InventoryGameInputController {

    public static final InventoryGameInputController INSTANCE = new InventoryGameInputController();

    public boolean handleKeyboardInput(int keycode, Camera camera) {

        if(keycode == Input.Keys.I) {
            InventoryRenderer.INSTANCE.setItemText(null);
            GameFlowControllerFacade.INSTANCE.setGameState(GameState.Sandbox);
        }

        if (keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
            System.exit(0);
        }


        camera.update();

        return true;
    }

}
