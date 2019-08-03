package com.mygdx.game.input.mouse;

import com.mygdx.game.logic.controller.GameFlowControllerFacade;

public class MouseInputControllerFacade implements Controller {

    public static final MouseInputControllerFacade INSTANCE = new MouseInputControllerFacade();

    private final GameFlowControllerFacade gameFlowControllerFacade = GameFlowControllerFacade.INSTANCE;
    private final BuilderMoueInputController builderMoueInputController = BuilderMoueInputController.INSTANCE;
    private final SandboxMouseInputController sandboxMouseInputController = SandboxMouseInputController.INSTANCE;
    private final InventoryMouseInputController inventoryMouseInputController = InventoryMouseInputController.INSTANCE;

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        switch (gameFlowControllerFacade.getGameState()) {
            case Inventory:
                return inventoryMouseInputController.touchDown(screenX, screenY, pointer, button);
            case Builder:
                return builderMoueInputController.touchDown(screenX, screenY, pointer, button);
            case Sandbox:
                return sandboxMouseInputController.touchDown(screenX, screenY, pointer, button);
            default:
                return false;
        }
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        switch (gameFlowControllerFacade.getGameState()) {
            case Inventory:
                return inventoryMouseInputController.mouseMoved(screenX, screenY);
            case Builder:
                return builderMoueInputController.mouseMoved(screenX, screenY);
            case Sandbox:
                return sandboxMouseInputController.mouseMoved(screenX, screenY);
            default:
                return false;
        }
    }
}
