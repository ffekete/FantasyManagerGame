package com.mygdx.game.input.mouse;

import com.mygdx.game.logic.controller.GameFlowControllerFacade;

public class MouseInputControllerFacade implements Controller {

    public static final MouseInputControllerFacade INSTANCE = new MouseInputControllerFacade();

    private final GameFlowControllerFacade gameFlowControllerFacade = GameFlowControllerFacade.INSTANCE;
    private final BuilderInputController builderInputController = BuilderInputController.INSTANCE;
    private final SandboxInputController sandboxInputController = SandboxInputController.INSTANCE;


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        switch (gameFlowControllerFacade.getGameState()) {
            case Builder:
                return builderInputController.touchDown(screenX, screenY, pointer, button);
            case Sandbox:
                return sandboxInputController.touchDown(screenX, screenY, pointer, button);
            default:
                return false;
        }
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        switch (gameFlowControllerFacade.getGameState()) {
            case Builder:
                return builderInputController.mouseMoved(screenX, screenY);
            case Sandbox:
                return sandboxInputController.mouseMoved(screenX, screenY);
            default:
                return false;
        }
    }
}
