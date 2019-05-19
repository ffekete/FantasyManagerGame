package com.mygdx.game.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Config;
import com.mygdx.game.creator.map.Cluster;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.logic.visibility.VisitedArea;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;
import com.mygdx.game.registry.WorldMapObjectRegistry;
import com.mygdx.game.renderer.camera.CameraPositionController;

import java.util.ArrayList;
import java.util.List;

public class WorldMapRenderer implements Renderer {

    public static final WorldMapRenderer INSTANCE = new WorldMapRenderer();

    private final TextureRegistry textureRegistry = TextureRegistry.INSTANCE;
    private final WorldMapObjectRegistry objectRegistry = WorldMapObjectRegistry.INSTANCE;
    private final CameraPositionController cameraPositionController = CameraPositionController.INSTANCE;

    @Override
    public void draw(Map2D map, SpriteBatch spriteBatch) {

        VisibilityMask visibilityMask = VisibilityMapRegistry.INSTANCE.getFor(map);

        if(visibilityMask != null)
            visibilityMask.mask(map, map.getVisitedareaMap());

        for (int i = Math.max((int)cameraPositionController.getCameraposition().getX()-40, 0); i < Math.min((int)cameraPositionController.getCameraposition().getX() + 40, Config.WorldMap.WORLD_WIDTH); i++) {
            for (int j = Math.max((int)cameraPositionController.getCameraposition().getY()-40, 0); j < Math.min((int)cameraPositionController.getCameraposition().getY() + 40, Config.WorldMap.WORLD_HEIGHT); j++) {
                if (map.getVisitedareaMap()[i][j] == VisitedArea.VISITED_BUT_NOT_VISIBLE) {
                    spriteBatch.setColor(Color.DARK_GRAY);
                } else {
                    spriteBatch.setColor(Color.WHITE);
                }
                if (map.getVisitedareaMap()[i][j] != VisitedArea.NOT_VISITED) {
                    Texture texture = textureRegistry.getForTile(map.getTile(i, j));
                    spriteBatch.draw(texture, i, j, 0, 0, 1, 1, 1, 1, 0, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
                }
            }
        }
    }
}
