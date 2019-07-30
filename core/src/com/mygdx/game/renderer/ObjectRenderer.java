package com.mygdx.game.renderer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.builder.BuildingBlock;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.logic.visibility.VisitedArea;
import com.mygdx.game.object.AnimatedObject;
import com.mygdx.game.object.ContainerObject;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.registry.AnimationRegistry;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;
import com.mygdx.game.registry.ObjectRegistry;
import com.mygdx.game.renderer.camera.CameraPositionController;
import com.mygdx.game.renderer.gui.component.GuiComponent;

import java.util.ArrayList;
import java.util.List;

public class ObjectRenderer implements Renderer {

    public static final ObjectRenderer INSTANCE = new ObjectRenderer();
    public final VisibilityMapRegistry visibilityMapRegistry = VisibilityMapRegistry.INSTANCE;

    private TextureRegion manaBarRegion;

    private final CameraPositionController cameraPositionController = CameraPositionController.INSTANCE;
    private final ObjectRegistry objectRegistry = ObjectRegistry.INSTANCE;
    private final TextureRegistry textureRegistry = TextureRegistry.INSTANCE;
    private final AnimationRegistry animationRegistry = AnimationRegistry.INSTANCE;

    public ObjectRenderer() {
        this.manaBarRegion = new TextureRegion(textureRegistry.getFor(GuiComponent.HUD), 340, 110, 500, 40);
    }

    @Override
    public void draw(Map2D dungeon, SpriteBatch spriteBatch) {

        CameraPositionController.Point cameraPosition = cameraPositionController.getCameraposition();

        // draw objects
        int x = (int) cameraPosition.getX() / Config.WorldMap.CLUSTER_DIVIDER;
        int y = (int) cameraPosition.getY() / Config.WorldMap.CLUSTER_DIVIDER;

        List<Cluster> clusters = new ArrayList<>();

        for (int i = -2; i <= 2; i++)
            for (int j = -2; j <= 2; j++) {
                if (x + i >= 0 && x + i <= Config.WorldMap.WORLD_WIDTH / Config.WorldMap.CLUSTER_DIVIDER &&
                        y + j >= 0 && y + j <= Config.WorldMap.WORLD_HEIGHT / Config.WorldMap.CLUSTER_DIVIDER) {
                    Cluster cluster = new Cluster(x + i, y + j);
                    clusters.add(cluster);
                }
            }

        for (Cluster cluster : clusters) {
            if (objectRegistry.getObjects(dungeon, cluster).isPresent())
                for (WorldObject worldObject : objectRegistry.getObjects(dungeon, cluster).get()) {
                    if(AnimatedObject.class.isAssignableFrom(worldObject.getClass())) {
                        if(dungeon.getVisitedareaMap()[(int)worldObject.getX()][(int) worldObject.getY()] == VisitedArea.VISIBLE)
                            animationRegistry.get((AnimatedObject) worldObject).drawKeyFrame(spriteBatch, worldObject.getX(), worldObject.getY(), 1, Direction.RIGHT);
                    } else {
                        spriteBatch.draw(textureRegistry.getForobject(worldObject.getClass()).get(getIndex(worldObject)), worldObject.getX(), worldObject.getY(), 0, 0, 1, 1, worldObject.getWorldMapSize(), worldObject.getWorldMapSize(), 0, 0, 0, textureRegistry.getForobject(worldObject.getClass()).get(getIndex(worldObject)).getWidth(), textureRegistry.getForobject(worldObject.getClass()).get(getIndex(worldObject)).getHeight(), false, false);
                    }

                    if(BuildingBlock.class.isAssignableFrom(worldObject.getClass())) {
                        spriteBatch.draw(manaBarRegion, worldObject.getX() + 0.2f, worldObject.getY() + 1.1f, 1.8f * ((float) ((BuildingBlock)worldObject).getProgress() / 100f), 0.1f);
                    }
                }
        }
    }

    private int getIndex(WorldObject object) {
        if(!ContainerObject.class.isAssignableFrom(object.getClass()))
            return 0;
        if(((ContainerObject) object).isOpened()) {
            return 1;
        }
        return 0;
    }
}
