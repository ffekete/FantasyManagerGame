package com.mygdx.game.input.keyboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.mygdx.game.logic.GameState;
import com.mygdx.game.logic.controller.GameFlowControllerFacade;
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
