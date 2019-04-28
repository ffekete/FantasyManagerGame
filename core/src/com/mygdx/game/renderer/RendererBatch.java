package com.mygdx.game.renderer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.logic.visibility.VisibilityMask;

import java.util.Arrays;
import java.util.List;

public enum RendererBatch implements Renderer {

    DUNGEON(
            Arrays.asList(MapRenderer.INSTANCE,
            ItemRenderer.INSTANCE,
            ActorRenderer.INSTANCE)
    );

    List<Renderer> renderers;

    RendererBatch( List<Renderer> renderers) {
        this.renderers = renderers;
    }

    @Override
    public void draw(Map2D dungeon, SpriteBatch spriteBatch) {
        for(Renderer renderer : renderers) {
            renderer.draw(dungeon, spriteBatch);
        }
    }
}
