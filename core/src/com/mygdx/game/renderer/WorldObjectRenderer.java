package com.mygdx.game.renderer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.creator.map.Cluster;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.object.AnimatedObject;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.registry.AnimationRegistry;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.registry.WorldMapObjectRegistry;
import com.mygdx.game.renderer.camera.CameraPositionController;

import java.util.ArrayList;
import java.util.List;

public class WorldObjectRenderer implements Renderer {

    public static final WorldObjectRenderer INSTANCE = new WorldObjectRenderer();

    private final CameraPositionController cameraPositionController = CameraPositionController.INSTANCE;
    private final WorldMapObjectRegistry objectRegistry = WorldMapObjectRegistry.INSTANCE;
    private final TextureRegistry textureRegistry = TextureRegistry.INSTANCE;
    private final AnimationRegistry animationRegistry = AnimationRegistry.INSTANCE;

    @Override
    public void draw(Map2D dungeon, SpriteBatch spriteBatch) {

        CameraPositionController.Point cameraPosition = cameraPositionController.getCameraposition();

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
                    if(AnimatedObject.class.isAssignableFrom(worldObject.getClass())) {
                        animationRegistry.get((AnimatedObject) worldObject).drawKeyFrame(spriteBatch, worldObject.getX(), worldObject.getY(), 1, Direction.RIGHT);
                    } else {
                        spriteBatch.draw(textureRegistry.getForobject(worldObject.getClass()), worldObject.getX(), worldObject.getY(), 0, 0, 1, 1, 1, 1, 0, 0, 0, textureRegistry.getForobject(worldObject.getClass()).getWidth(), textureRegistry.getForobject(worldObject.getClass()).getHeight(), false, false);
                    }
                }
        }
    }
}
