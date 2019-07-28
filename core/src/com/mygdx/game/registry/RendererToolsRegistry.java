package com.mygdx.game.registry;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RendererToolsRegistry {

    public static final RendererToolsRegistry INSTANCE = new RendererToolsRegistry();

    private SpriteBatch spriteBatch;
    private BitmapFont bitmapFont;

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public BitmapFont getBitmapFont() {
        return bitmapFont;
    }

    public void setSpriteBatch(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    public void setBitmapFont(BitmapFont bitmapFont) {
        this.bitmapFont = bitmapFont;
    }

    private RendererToolsRegistry() {}
}
