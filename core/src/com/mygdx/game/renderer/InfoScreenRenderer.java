package com.mygdx.game.renderer;

import com.mygdx.game.Config;
import com.mygdx.game.item.resources.Wood;
import com.mygdx.game.logic.controller.SandboxGameLogicController;
import com.mygdx.game.logic.time.DayTimeCalculator;
import com.mygdx.game.object.StorageArea;
import com.mygdx.game.registry.RendererToolsRegistry;
import com.mygdx.game.registry.StorageRegistry;
import com.mygdx.game.registry.TextureRegistry;

public class InfoScreenRenderer {

    public static final InfoScreenRenderer INSTANCE = new InfoScreenRenderer();

    private final RendererToolsRegistry rendererToolsRegistry = RendererToolsRegistry.INSTANCE;

    public void draw() {
        RendererToolsRegistry.INSTANCE.getInfoViewPort().apply();
        RendererToolsRegistry.INSTANCE.getSpriteBatch().setProjectionMatrix(rendererToolsRegistry.getInfoCamera().combined);


        RendererToolsRegistry.INSTANCE.getSpriteBatch().draw(TextureRegistry.INSTANCE.getFor(Wood.class), 10, Config.Screen.HEIGHT - 60, 50,50);
        rendererToolsRegistry.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), ": " + getWoodAmount(), 65, Config.Screen.HEIGHT - 25);
        rendererToolsRegistry.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "Hour: " + DayTimeCalculator.INSTANCE.getHour() + " Day: " + DayTimeCalculator.INSTANCE.getDay(), 10, 50);
        rendererToolsRegistry.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), SandboxGameLogicController.INSTANCE.isPaused() ? "Paused" : "", Config.Screen.CANVAS_WIDTH / 2, Config.Screen.HEIGHT / 2);

    }

    private int getWoodAmount() {
        return StorageRegistry.INSTANCE.getStorages().stream()
                .filter(s -> Wood.class.equals(s.getStoredItem()))
                .mapToInt(StorageArea::getStoredAmount)
                .sum();
    }
}
