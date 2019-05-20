package com.mygdx.game.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.mygdx.game.Config;
import com.mygdx.game.creator.TileBase;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.logic.visibility.VisitedArea;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;
import com.mygdx.game.renderer.camera.CameraPositionController;
import com.mygdx.game.renderer.selector.TileSelector;

public class DungeonRenderer implements Renderer {

    public static final DungeonRenderer INSTANCE = new DungeonRenderer();
    public final CameraPositionController cameraPositionController = CameraPositionController.INSTANCE;

    private TileSelector tileSelector = new TileSelector();

    @Override
    public void draw(Map2D map, SpriteBatch spriteBatch) {

        VisibilityMask visibilityMask = VisibilityMapRegistry.INSTANCE.getFor(map);

        // if nothing to calculate for now
        if(visibilityMask == null)
            return;

        visibilityMask.mask(map, map.getVisitedareaMap());

        for (int i = Math.max((int)cameraPositionController.getCameraposition().getX()-40, 0); i < Math.min((int)cameraPositionController.getCameraposition().getX() + 40, Config.Dungeon.DUNGEON_WIDTH); i++) {
            for (int j = Math.max((int)cameraPositionController.getCameraposition().getY()-40, 0); j < Math.min((int)cameraPositionController.getCameraposition().getY() + 40, Config.Dungeon.DUNGEON_HEIGHT); j++) {

                if (map.getVisitedareaMap()[i][j] == VisitedArea.VISITED_BUT_NOT_VISIBLE) {
                    spriteBatch.setColor(Color.valueOf("222222"));
                } else {
                    spriteBatch.setColor(Color.valueOf("444444"));
                }
                if (map.getVisitedareaMap()[i][j] != VisitedArea.NOT_VISITED) {
                    TextureRegion texture = tileSelector.getFor(map, i,j);

                    spriteBatch.draw(texture, i , j, 1, 1);
                }
            }
        }
    }
}
