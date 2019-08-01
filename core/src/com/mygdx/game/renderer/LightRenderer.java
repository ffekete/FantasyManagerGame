package com.mygdx.game.renderer;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.light.LightSource;
import com.mygdx.game.registry.LightSourceRegistry;
import com.mygdx.game.registry.TextureRegistry;

public class LightRenderer implements Renderer<Map2D> {

    public static final LightRenderer INSTANCE = new LightRenderer();

    private final LightSourceRegistry lightSourceRegistry = LightSourceRegistry.INSTANCE;
    private final TextureRegistry textureRegistry = TextureRegistry.INSTANCE;

    private LightRenderer() {
    }

    @Override
    public void draw(Map2D map, SpriteBatch spriteBatch) {

        spriteBatch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_DST_ALPHA);

        for(LightSource light : lightSourceRegistry.getFor(map)) {
            light.update();
            spriteBatch.setColor(light.getColor());
            spriteBatch.draw(textureRegistry.getFor(light.getType()),light.getX() - light.getArea() / 2 + 0.5f, light.getY() - light.getArea() / 2 + 0.5f, (int)light.getArea(), (int)light.getArea());
        }

        spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

    }
}
