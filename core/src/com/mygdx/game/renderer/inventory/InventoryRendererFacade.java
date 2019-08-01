package com.mygdx.game.renderer.inventory;

import com.mygdx.game.logic.controller.InventoryGameLogicController;
import com.mygdx.game.registry.RendererToolsRegistry;

public class InventoryRendererFacade {

    public static final InventoryRendererFacade INSTANCE = new InventoryRendererFacade();

    private final InventoryRenderer inventoryRenderer = InventoryRenderer.INSTANCE;
    private final InventoryGameLogicController inventoryGameLogicController = InventoryGameLogicController.INSTANCE;
    private final RendererToolsRegistry rendererToolsRegistry = RendererToolsRegistry.INSTANCE;

    public void draw() {
        inventoryRenderer.draw(inventoryGameLogicController.getInventory(), rendererToolsRegistry.getSpriteBatch());
    }

}
