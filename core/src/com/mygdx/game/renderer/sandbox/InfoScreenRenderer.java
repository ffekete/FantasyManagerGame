package com.mygdx.game.renderer.sandbox;

import com.mygdx.game.registry.RendererToolsRegistry;

public class InfoScreenRenderer {

    public static final InfoScreenRenderer INSTANCE = new InfoScreenRenderer();

    public void draw() {
        //RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "Space: pause, M: show next map, N: next character, F: toggle focus", 10, 40);
    }
}
