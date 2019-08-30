package com.mygdx.game.renderer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Renderer<T> {
    void draw(T item, SpriteBatch spriteBatch);
}
