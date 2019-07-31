package com.mygdx.game.renderer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.builder.BuildingBlock;
import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.logic.visibility.VisitedArea;
import com.mygdx.game.object.AnimatedObject;
import com.mygdx.game.object.ContainerObject;
import com.mygdx.game.object.TileableObject;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.registry.*;
import com.mygdx.game.renderer.camera.CameraPositionController;
import com.mygdx.game.renderer.gui.component.GuiComponent;
import com.mygdx.game.renderer.selector.WallTileSelector;

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
    private final RendererToolsRegistry rendererToolsRegistry = RendererToolsRegistry.INSTANCE;

    public ObjectRenderer() {
        this.manaBarRegion = new TextureRegion(textureRegistry.getFor(GuiComponent.HUD), 340, 110, 500, 40);
    }

    @Override
    public void draw(Map2D dungeon, SpriteBatch spriteBatch) {

        Vector3 cameraPosition = RendererToolsRegistry.INSTANCE.getCamera().position;

        // draw objects
        int startY = (int) rendererToolsRegistry.getCamera().position.y;
        int lengthY = (int) rendererToolsRegistry.getCamera().viewportHeight;

        int startX = (int) rendererToolsRegistry.getCamera().position.x;
        int lengthX = (int) rendererToolsRegistry.getCamera().viewportWidth;


        for (int i = Math.max(0, startX - lengthX / 2); i < Math.min(startX  + lengthX / 2, dungeon.getWidth() - 1); i++) {
            for (int j = Math.min(startY + lengthY / 2, dungeon.getHeight() - 1); j > Math.max(0, startY - lengthY / 2); j--) {
                WorldObject worldObject = objectRegistry.getObjectGrid().get(dungeon)[i][j];
                if (worldObject != null) {

                    if (TileableObject.class.isAssignableFrom(worldObject.getClass())) {
                        spriteBatch.draw(WallTileSelector.INSTANCE.getFor(objectRegistry.getObjectGrid().get(dungeon), worldObject), worldObject.getX(), worldObject.getY(), 1, 1);
                    } else if (AnimatedObject.class.isAssignableFrom(worldObject.getClass())) {
                        if (dungeon.getVisitedareaMap()[(int) worldObject.getX()][(int) worldObject.getY()] == VisitedArea.VISIBLE)
                            animationRegistry.get((AnimatedObject) worldObject).drawKeyFrame(spriteBatch, worldObject.getX(), worldObject.getY(), 1, Direction.RIGHT);
                    } else {
                        spriteBatch.draw(textureRegistry.getForobject(worldObject.getClass()).get(getIndex(worldObject)), worldObject.getX(), worldObject.getY(), 0, 0, 1, 1, worldObject.getWorldMapSize(), worldObject.getWorldMapSize(), 0, 0, 0, textureRegistry.getForobject(worldObject.getClass()).get(getIndex(worldObject)).getWidth(), textureRegistry.getForobject(worldObject.getClass()).get(getIndex(worldObject)).getHeight(), false, false);
                    }

                    if (BuildingBlock.class.isAssignableFrom(worldObject.getClass())) {
                        spriteBatch.draw(manaBarRegion, worldObject.getX() + 0.2f, worldObject.getY() + 1.1f, 1.8f * ((float) ((BuildingBlock) worldObject).getProgress() / 100f), 0.1f);
                    }
                }
            }
        }
    }

    private int getIndex(WorldObject object) {
        if (!ContainerObject.class.isAssignableFrom(object.getClass()))
            return 0;
        if (((ContainerObject) object).isOpened()) {
            return 1;
        }
        return 0;
    }
}
