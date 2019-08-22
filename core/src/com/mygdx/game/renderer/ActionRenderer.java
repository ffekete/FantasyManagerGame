package com.mygdx.game.renderer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.logic.action.manager.ActionManager;
import com.mygdx.game.map.Map2D;

public class ActionRenderer implements Renderer<Map2D> {

    public static final ActionRenderer INSTANCE = new ActionRenderer();

    public ActionRenderer() {

    }

    @Override
    public void draw(Map2D dungeon, SpriteBatch spriteBatch) {
        ActionManager.INSTANCE.update();
    }


}
