package com.mygdx.game.renderer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.map.Map2D;

public interface Renderer<T> {
    void draw(T item, SpriteBatch spriteBatch);
}
