package com.mygdx.game.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.builder.BuildingBlock;
import com.mygdx.game.logic.visibility.VisitedArea;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.AnimatedObject;
import com.mygdx.game.object.ContainerObject;
import com.mygdx.game.object.TileableWallObject;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.decoration.Decoration;
import com.mygdx.game.object.decoration.Rotatable;
import com.mygdx.game.object.floor.Floor;
import com.mygdx.game.registry.*;
import com.mygdx.game.renderer.gui.component.GuiComponent;
import com.mygdx.game.renderer.selector.WallTileSelector;

public class ObjectRenderer implements Renderer<Map2D> {

    public static final ObjectRenderer INSTANCE = new ObjectRenderer();

    private TextureRegion progressBarRegion;

    private final ObjectRegistry objectRegistry = ObjectRegistry.INSTANCE;
    private final TextureRegistry textureRegistry = TextureRegistry.INSTANCE;
    private final AnimationRegistry animationRegistry = AnimationRegistry.INSTANCE;
    private final RendererToolsRegistry rendererToolsRegistry = RendererToolsRegistry.INSTANCE;

    private WorldObject groundObject;

    public ObjectRenderer() {
        this.progressBarRegion = new TextureRegion(textureRegistry.getFor(GuiComponent.HUD), 340, 110, 500, 40);
    }

    @Override
    public void draw(Map2D dungeon, SpriteBatch spriteBatch) {

        // draw objects
        int startY = (int) rendererToolsRegistry.getCamera().position.y;
        int lengthY = (int) rendererToolsRegistry.getCamera().viewportHeight;

        int startX = (int) rendererToolsRegistry.getCamera().position.x;
        int lengthX = (int) rendererToolsRegistry.getCamera().viewportWidth;


        for (int i = Math.min(startX + lengthX / 2, dungeon.getWidth() - 1); i >= Math.max(0, startX - lengthX / 2); i--) {
            for (int j = Math.min(startY + lengthY / 2, dungeon.getHeight() - 1); j >= Math.max(0, startY - lengthY / 2); j--) {
                WorldObject worldObject = objectRegistry.getObjectGrid().get(dungeon)[i][j][1];
                if (worldObject != null) {


                    if (dungeon.getVisitedareaMap()[i][j] == VisitedArea.VISITED_BUT_NOT_VISIBLE) {
                        spriteBatch.setColor(Color.DARK_GRAY);
                    } else {
                        // setting transparency if the view is blocked by decoration
                        if (Decoration.class.isAssignableFrom(worldObject.getClass()) && isActorAdjacent(dungeon, i, j)) {
                            spriteBatch.setColor(Color.valueOf("FFFFFF88"));
                        } else {
                            spriteBatch.setColor(Color.valueOf("FFFFFFFF"));
                        }
                    }

                    if (dungeon.getVisitedareaMap()[i][j] != VisitedArea.NOT_VISITED) {
                        // skipping ground objects
                        if (Floor.class.isAssignableFrom(worldObject.getClass())) {
                            continue;
                        }

                        if (TileableWallObject.class.isAssignableFrom(worldObject.getClass())) {
                            spriteBatch.draw(WallTileSelector.INSTANCE.getFor(objectRegistry.getObjectGrid().get(dungeon), worldObject), worldObject.getX(), worldObject.getY(), 1, 1);
                        } else if (AnimatedObject.class.isAssignableFrom(worldObject.getClass())) {
                            if (dungeon.getVisitedareaMap()[(int) worldObject.getX()][(int) worldObject.getY()] == VisitedArea.VISIBLE)
                                animationRegistry.get((AnimatedObject) worldObject).drawKeyFrame(spriteBatch, worldObject.getX(), worldObject.getY(), 1, Direction.RIGHT);
                        } else {

                            boolean flipX = false, flipY = false;

                            if (Rotatable.class.isAssignableFrom(worldObject.getClass())) {
                                flipX = ((Rotatable) worldObject).getFlipX();
                                flipY = ((Rotatable) worldObject).getFlipY();
                            }

                            if(Decoration.class.isAssignableFrom(worldObject.getClass())) {
                                spriteBatch.setColor(Color.valueOf("FFFFFF22"));
                                spriteBatch.draw(textureRegistry.getShadowTexture(), worldObject.getX(), worldObject.getY(), 0,0, 1,1, worldObject.getWorldMapSize(), worldObject.getWorldMapSize(), 0,0,0, 32, 32, false, false);

                                spriteBatch.setColor(Color.valueOf("FFFFFFFF"));
                            }

                            spriteBatch.draw(textureRegistry.getForobject(worldObject.getClass()).get(getIndex(worldObject)), worldObject.getX(), worldObject.getY(), 0, 0, 1, 1, worldObject.getWorldMapSize(), worldObject.getWorldMapSize(), 0, 0, 0, textureRegistry.getForobject(worldObject.getClass()).get(getIndex(worldObject)).getWidth(), textureRegistry.getForobject(worldObject.getClass()).get(getIndex(worldObject)).getHeight(), flipX, flipY);
                        }

                        if (BuildingBlock.class.isAssignableFrom(worldObject.getClass())) {
                            spriteBatch.draw(progressBarRegion, worldObject.getX() + 0.2f, worldObject.getY() + 1.1f, 1.8f * ((float) ((BuildingBlock) worldObject).getProgress() / 100f), 0.1f);
                        }

                        // rendering ground object progress bars here
                        groundObject = objectRegistry.getObjectGrid().get(dungeon)[i][j][0];
                        if (groundObject != null && BuildingBlock.class.isAssignableFrom(groundObject.getClass())) {
                            spriteBatch.draw(progressBarRegion, groundObject.getX() + 0.2f, groundObject.getY() + 1.1f, 1.8f * ((float) ((BuildingBlock) groundObject).getProgress() / 100f), 0.1f);
                        }
                    }
                }
            }
        }

        spriteBatch.setColor(Color.valueOf("FFFFFFFF"));
    }

    private boolean isActorAdjacent(Map2D dungeon, int i, int j) {
        return
                ((i - 1 >= 0 && j + 1 < dungeon.getHeight() && ActorRegistry.INSTANCE.getActorGrid().get(dungeon)[i - 1][j + 1] != null) ||
                        ActorRegistry.INSTANCE.getActorGrid().get(dungeon)[i][j] != null ||
                        (j + 1 < dungeon.getHeight() && ActorRegistry.INSTANCE.getActorGrid().get(dungeon)[i][j + 1] != null) ||
                        (i + 1 < dungeon.getWidth() && j + 1 < dungeon.getHeight() && ActorRegistry.INSTANCE.getActorGrid().get(dungeon)[i + 1][j+1] != null));
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
