package com.mygdx.game.renderer.building;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.renderer.*;

import java.util.Arrays;
import java.util.List;

public enum BuildingRendererBatch implements Renderer {

    WORLD_MAP(
            Arrays.asList(WorldMapRenderer.INSTANCE,
                    ItemRenderer.INSTANCE,
                    ActorRenderer.INSTANCE,
                    ObjectRenderer.INSTANCE,
                    LightRenderer.INSTANCE
            )
    ),

    DUNGEON(
            Arrays.asList(DungeonRenderer.INSTANCE,
                    ItemRenderer.INSTANCE,
                    ActorRenderer.INSTANCE,
                    ObjectRenderer.INSTANCE,
                    LightRenderer.INSTANCE
            )
    );

    List<Renderer> renderers;

    BuildingRendererBatch(List<Renderer> renderers) {
        this.renderers = renderers;
    }

    @Override
    public void draw(Map2D dungeon, SpriteBatch spriteBatch) {
        for (Renderer renderer : renderers) {
            renderer.draw(dungeon, spriteBatch);
        }
        spriteBatch.setColor(Color.WHITE);
    }
}
