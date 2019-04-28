package com.mygdx.game.animation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public interface Animation extends Disposable {
    void drawKeyFrame(SpriteBatch spriteBatch, float x, float y, int scale, boolean flip);
}
