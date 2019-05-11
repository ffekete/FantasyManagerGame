package com.mygdx.game.registry;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteBatchRegistry {

    public static final SpriteBatchRegistry INSTANCE = new SpriteBatchRegistry();

    private SpriteBatch spriteBatch;

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public void setSpriteBatch(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    private SpriteBatchRegistry() {}
}
