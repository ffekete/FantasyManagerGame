package com.mygdx.game.renderer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Config;
import com.mygdx.game.logic.action.manager.ActionManager;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.renderer.camera.CameraPositionController;

public class ActionRenderer implements Renderer<Map2D> {

    public static final ActionRenderer INSTANCE = new ActionRenderer();

    public ActionRenderer() {

    }

    @Override
    public void draw(Map2D dungeon, SpriteBatch spriteBatch) {
        if (CameraPositionController.INSTANCE.getZoom() > Config.Engine.ZOOM_MAX_TO_SMALL_MAP) {
            return;
        }
        ActionManager.INSTANCE.update();
    }


}
