package com.mygdx.game.renderer.building;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.RendererToolsRegistry;

public class BuildingRenderer {

    public static final BuildingRenderer INSTANCE = new BuildingRenderer();

    private final RendererToolsRegistry rendererToolsRegistry = RendererToolsRegistry.INSTANCE;

    public void draw() {
        if(Map2D.MapType.WORLD_MAP.equals(MapRegistry.INSTANCE.getCurrentMapToShow().getMapType()))
            BuildingRendererBatch.WORLD_MAP.draw(MapRegistry.INSTANCE.getCurrentMapToShow(), RendererToolsRegistry.INSTANCE.getSpriteBatch());
        else
            BuildingRendererBatch.DUNGEON.draw(MapRegistry.INSTANCE.getCurrentMapToShow(), RendererToolsRegistry.INSTANCE.getSpriteBatch());

        rendererToolsRegistry.getShapeRenderer().setProjectionMatrix(rendererToolsRegistry.getCamera().combined);

        drawGrid();
        rendererToolsRegistry.getShapeRenderer().end();
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
