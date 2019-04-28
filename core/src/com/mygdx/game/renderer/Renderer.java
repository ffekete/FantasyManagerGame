package com.mygdx.game.renderer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.creator.map.Map2D;

public interface Renderer {
    void draw(Map2D dungeon, SpriteBatch spriteBatch);
}
