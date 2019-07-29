package com.mygdx.game.renderer.sandbox;

import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.RendererToolsRegistry;

public class SandboxRenderer {

    public static final SandboxRenderer INSTANCE = new SandboxRenderer();

    public void draw() {
        if(Map2D.MapType.WORLD_MAP.equals(MapRegistry.INSTANCE.getCurrentMapToShow().getMapType()))
            SandboxRendererBatch.WORLD_MAP.draw(MapRegistry.INSTANCE.getCurrentMapToShow(), RendererToolsRegistry.INSTANCE.getSpriteBatch());
        else
            SandboxRendererBatch.DUNGEON.draw(MapRegistry.INSTANCE.getCurrentMapToShow(), RendererToolsRegistry.INSTANCE.getSpriteBatch());

        if (false) {
            // low fps test
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
