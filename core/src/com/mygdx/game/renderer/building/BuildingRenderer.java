package com.mygdx.game.renderer.building;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.logic.GameState;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.RendererToolsRegistry;
import com.mygdx.game.renderer.camera.CameraPositionController;
import com.mygdx.game.renderer.InfoScreenRenderer;
import com.mygdx.game.stage.StageConfigurer;
import com.mygdx.game.utils.GdxUtils;

public class BuildingRenderer {

    public static final BuildingRenderer INSTANCE = new BuildingRenderer();

    private final RendererToolsRegistry rendererToolsRegistry = RendererToolsRegistry.INSTANCE;

    public void draw() {

        RendererToolsRegistry.INSTANCE.getSpriteBatch().setProjectionMatrix(RendererToolsRegistry.INSTANCE.getCamera().combined);
        RendererToolsRegistry.INSTANCE.getSpriteBatch().begin();

        GdxUtils.clearScreen();
        CameraPositionController.INSTANCE.updateCamera((OrthographicCamera) RendererToolsRegistry.INSTANCE.getCamera());

        if(Map2D.MapType.WORLD_MAP.equals(MapRegistry.INSTANCE.getCurrentMapToShow().getMapType()))
            BuildingRendererBatch.WORLD_MAP.draw(MapRegistry.INSTANCE.getCurrentMapToShow(), RendererToolsRegistry.INSTANCE.getSpriteBatch());
        else
            BuildingRendererBatch.DUNGEON.draw(MapRegistry.INSTANCE.getCurrentMapToShow(), RendererToolsRegistry.INSTANCE.getSpriteBatch());

        RendererToolsRegistry.INSTANCE.getSpriteBatch().end();

        rendererToolsRegistry.getShapeRenderer().setProjectionMatrix(rendererToolsRegistry.getCamera().combined);

        drawGrid();

        rendererToolsRegistry.getShapeRenderer().end();

        RendererToolsRegistry.INSTANCE.getSpriteBatch().begin();
        InfoScreenRenderer.INSTANCE.draw();
        RendererToolsRegistry.INSTANCE.getSpriteBatch().end();

        StageConfigurer.INSTANCE.getFor(GameState.Builder).act();
        StageConfigurer.INSTANCE.getFor(GameState.Builder).draw();
    }

    private void drawGrid() {
        rendererToolsRegistry.getShapeRenderer().begin(ShapeRenderer.ShapeType.Line);
        rendererToolsRegistry.getShapeRenderer().setColor(Color.valueOf("333333"));

        int startY = (int)rendererToolsRegistry.getCamera().position.y;
        int lengthY = (int)rendererToolsRegistry.getCamera().viewportHeight - (int)rendererToolsRegistry.getCamera().position.y;

        int startX = (int)rendererToolsRegistry.getCamera().position.x;
        int lengthX = (int)rendererToolsRegistry.getCamera().viewportWidth - (int)rendererToolsRegistry.getCamera().position.x;


        for(int i = startY-lengthY; i < startY + lengthY; i++) {
            rendererToolsRegistry.getShapeRenderer().line(startX - lengthX, i, startX + lengthX, i);
        }

        for(int i = startX-lengthX; i < startX + lengthX; i++) {
            rendererToolsRegistry.getShapeRenderer().line(i, startY - lengthY, i, startY + lengthY);
        }
    }
}
