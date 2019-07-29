package com.mygdx.game.logic.input;

import com.badlogic.gdx.graphics.Camera;
import com.mygdx.game.logic.controller.GameFlowControllerFacade;

public class GameInputControllerFacade {

    public static final GameInputControllerFacade INSTANCE = new GameInputControllerFacade();

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
