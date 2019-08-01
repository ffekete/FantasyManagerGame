package com.mygdx.game.renderer;

import com.mygdx.game.logic.controller.GameFlowControllerFacade;
import com.mygdx.game.renderer.building.BuildingRenderer;
import com.mygdx.game.renderer.inventory.InventoryRenderer;
import com.mygdx.game.renderer.inventory.InventoryRendererFacade;
import com.mygdx.game.renderer.sandbox.SandboxRenderer;

public class RenderingFacade {

    public static final RenderingFacade INSTANCE = new RenderingFacade();

    private final GameFlowControllerFacade gameFlowControllerFacade = GameFlowControllerFacade.INSTANCE;

    public void draw() {
        switch (gameFlowControllerFacade.getGameState()) {
            case Inventory:
                InventoryRendererFacade.INSTANCE.draw();
                break;
            case Sandbox:
                SandboxRenderer.INSTANCE.draw();
                break;
            case Builder:
                BuildingRenderer.INSTANCE.draw();
                break;
        }
    }

}
