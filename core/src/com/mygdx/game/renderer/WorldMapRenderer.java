package com.mygdx.game.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Config;
import com.mygdx.game.creator.TileBase;
import com.mygdx.game.creator.map.Cluster;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.creator.map.object.WorldObject;
import com.mygdx.game.logic.Point;
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

        CameraPositionController.Point cameraPosition = cameraPositionController.getCameraposition();

        for (int i = Math.max((int)cameraPositionController.getCameraposition().getX()-20, 0); i < Math.max((int)cameraPositionController.getCameraposition().getX() + 20, Config.WorldMap.WORLD_WIDTH); i++) {
            for (int j = Math.max((int)cameraPositionController.getCameraposition().getY()-20, 0); j < Math.max((int)cameraPositionController.getCameraposition().getY() + 20, Config.WorldMap.WORLD_HEIGHT); j++) {
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

        // draw objects
        int x = (int) cameraPosition.getX() / Config.WorldMap.CLUSTER_DIVIDER;
        int y = (int) cameraPosition.getY() / Config.WorldMap.CLUSTER_DIVIDER;

        List<Cluster> clusters = new ArrayList<>();

        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++) {
                if (x + i >= 0 && x + i <= Config.WorldMap.WORLD_WIDTH / Config.WorldMap.CLUSTER_DIVIDER &&
                        y + j >= 0 && y + j <= Config.WorldMap.WORLD_HEIGHT / Config.WorldMap.CLUSTER_DIVIDER) {
                    Cluster cluster = new Cluster(x + i, y + j);
                    clusters.add(cluster);
                }
            }

        for (Cluster cluster : clusters) {
            if (objectRegistry.getObjects(cluster).isPresent())
                for (WorldObject worldObject : objectRegistry.getObjects(cluster).get()) {
                    Texture texture = textureRegistry.getForobject(worldObject.getClass());
                    spriteBatch.draw(texture, worldObject.getX(), worldObject.getY(), 0, 0, 1, 1, 1, 1, 0, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
                }
        }

    }
}
