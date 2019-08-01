package com.mygdx.game.input.keyboard;

import com.badlogic.gdx.graphics.Camera;
import com.mygdx.game.logic.controller.GameFlowControllerFacade;

public class KeyboardInputControllerFacade {

    public static final KeyboardInputControllerFacade INSTANCE = new KeyboardInputControllerFacade();

    private final GameFlowControllerFacade gameFlowControllerFacade = GameFlowControllerFacade.INSTANCE;

    public boolean processInput(int keyCode, Camera camera) {

        switch (gameFlowControllerFacade.getGameState()) {
            case Builder:
                return BuildingGameInputController.INSTANCE.handleKeyboardInput(keyCode, camera);
            case Sandbox:
                return SandboxGameInputController.INSTANCE.handleKeyboardInput(keyCode, camera);
            default:
                return false;
        }
    }

}
