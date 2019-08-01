package com.mygdx.game.renderer.sandbox;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.logic.GameState;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.RendererToolsRegistry;
import com.mygdx.game.renderer.camera.CameraPositionController;
import com.mygdx.game.utils.GdxUtils;

public class SandboxRenderer {

    public static final SandboxRenderer INSTANCE = new SandboxRenderer();

    private final RendererToolsRegistry rendererToolsRegistry = RendererToolsRegistry.INSTANCE;

    public void draw() {

        RendererToolsRegistry.INSTANCE.getSpriteBatch().setProjectionMatrix(RendererToolsRegistry.INSTANCE.getCamera().combined);
        RendererToolsRegistry.INSTANCE.getSpriteBatch().begin();

        GdxUtils.clearScreen();
        CameraPositionController.INSTANCE.updateCamera((OrthographicCamera) RendererToolsRegistry.INSTANCE.getCamera());

        if(Map2D.MapType.WORLD_MAP.equals(MapRegistry.INSTANCE.getCurrentMapToShow().getMapType()))
            SandboxRendererBatch.WORLD_MAP.draw(MapRegistry.INSTANCE.getCurrentMapToShow(), RendererToolsRegistry.INSTANCE.getSpriteBatch());
        else
            SandboxRendererBatch.DUNGEON.draw(MapRegistry.INSTANCE.getCurrentMapToShow(), RendererToolsRegistry.INSTANCE.getSpriteBatch());

        RendererToolsRegistry.INSTANCE.getSpriteBatch().end();

        RendererToolsRegistry.INSTANCE.getSpriteBatch().begin();
        InfoScreenRenderer.INSTANCE.draw();
        RendererToolsRegistry.INSTANCE.getSpriteBatch().end();

        if (false) {
            // low fps test
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        rendererToolsRegistry.getStage(GameState.Sandbox).act();
        rendererToolsRegistry.getStage(GameState.Sandbox).draw();
    }
}
