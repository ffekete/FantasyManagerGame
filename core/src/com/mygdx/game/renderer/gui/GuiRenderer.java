package com.mygdx.game.renderer.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.actor.Actor;

public interface GuiRenderer {

    void render(SpriteBatch spriteBatch, Actor actor);

}
