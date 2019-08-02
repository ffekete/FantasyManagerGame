package com.mygdx.game.renderer;

import com.mygdx.game.Config;
import com.mygdx.game.logic.controller.SandboxGameLogicController;
import com.mygdx.game.logic.time.DayTimeCalculator;
import com.mygdx.game.registry.RendererToolsRegistry;

public class InfoScreenRenderer {

    public static final InfoScreenRenderer INSTANCE = new InfoScreenRenderer();

    private final RendererToolsRegistry rendererToolsRegistry = RendererToolsRegistry.INSTANCE;

    public void draw() {
        RendererToolsRegistry.INSTANCE.getInfoViewPort().apply();
        RendererToolsRegistry.INSTANCE.getSpriteBatch().setProjectionMatrix(rendererToolsRegistry.getInfoCamera().combined);


        rendererToolsRegistry.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "Hour: " + DayTimeCalculator.INSTANCE.getHour() + " Day: " + DayTimeCalculator.INSTANCE.getDay(), 10, 70);
        rendererToolsRegistry.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), SandboxGameLogicController.INSTANCE.isPaused() ? "Paused" : "", Config.Screen.CANVAS_WIDTH / 2, Config.Screen.HEIGHT / 2);

    }
}
