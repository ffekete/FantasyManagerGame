package com.mygdx.game.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.logic.visibility.VisitedArea;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.Dungeon;
import com.mygdx.game.map.dungeon.DungeonType;
import com.mygdx.game.registry.VisibilityMapRegistry;
import com.mygdx.game.renderer.camera.CameraPositionController;
import com.mygdx.game.renderer.selector.CaveTileSelector;
import com.mygdx.game.renderer.selector.RoomsTileSelector;

public class DungeonRenderer implements Renderer {

    public static final DungeonRenderer INSTANCE = new DungeonRenderer();
    public final CameraPositionController cameraPositionController = CameraPositionController.INSTANCE;

    private CaveTileSelector caveTileSelector = new CaveTileSelector();
    private RoomsTileSelector roomsTileSelector = new RoomsTileSelector();

    @Override
    public void draw(Map2D map, SpriteBatch spriteBatch) {

        VisibilityMask visibilityMask = VisibilityMapRegistry.INSTANCE.getFor(map);

        // if nothing to calculate for now
        if(visibilityMask == null)
            return;

        visibilityMask.mask(map, map.getVisitedareaMap());

        for (int i = Math.max((int)cameraPositionController.getCameraposition().getX()-40, 0); i < Math.min((int)cameraPositionController.getCameraposition().getX() + 40, map.getWidth()); i++) {
            for (int j = Math.max((int)cameraPositionController.getCameraposition().getY()-40, 0); j < Math.min((int)cameraPositionController.getCameraposition().getY() + 40, map.getHeight()); j++) {

                if (map.getVisitedareaMap()[i][j] == VisitedArea.VISITED_BUT_NOT_VISIBLE) {
                    spriteBatch.setColor(Color.valueOf("222222"));
                } else {
                    spriteBatch.setColor(Color.valueOf("444444"));
                }
                if (map.getVisitedareaMap()[i][j] != VisitedArea.NOT_VISITED) {

                    TextureRegion texture;
                    if(DungeonType.ROOMS.equals(((Dungeon)map).getDungeonType())) {
                        texture = roomsTileSelector.getFor(map, i, j);
                    } else {
                        texture = caveTileSelector.getFor(map, i, j);
                    }

                    spriteBatch.draw(texture, i , j, 1, 1);
                }
            }
        }
    }
}
