package com.mygdx.game.renderer.inventory;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.actor.inventory.Inventory;
import com.mygdx.game.logic.GameState;
import com.mygdx.game.registry.RendererToolsRegistry;
import com.mygdx.game.renderer.Renderer;
import com.mygdx.game.utils.GdxUtils;

public class InventoryRenderer implements Renderer<Inventory> {

    public static final InventoryRenderer INSTANCE = new InventoryRenderer();

    private final RendererToolsRegistry rendererToolsRegistry = RendererToolsRegistry.INSTANCE;

    @Override
    public void draw(Inventory inventory, SpriteBatch spriteBatch) {
        GdxUtils.clearScreen();
        rendererToolsRegistry.getStage(GameState.Inventory).act();
        rendererToolsRegistry.getStage(GameState.Inventory).draw();
    }
}
