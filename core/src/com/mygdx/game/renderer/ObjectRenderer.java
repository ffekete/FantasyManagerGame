package com.mygdx.game.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.builder.BuildingBlock;
import com.mygdx.game.logic.time.DayTimeCalculator;
import com.mygdx.game.logic.visibility.VisitedArea;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.*;
import com.mygdx.game.object.decoration.Decoration;
import com.mygdx.game.object.decoration.River;
import com.mygdx.game.object.decoration.Rotatable;
import com.mygdx.game.object.floor.Floor;
import com.mygdx.game.registry.*;
import com.mygdx.game.renderer.camera.CameraPositionController;
import com.mygdx.game.renderer.gui.component.GuiComponent;
import com.mygdx.game.renderer.selector.TileSelector;

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

        if (CameraPositionController.INSTANCE.getZoom() > Config.Engine.ZOOM_MAX_TO_SMALL_MAP) {
            return;
        }

        // draw objects
        int startY = (int) rendererToolsRegistry.getCamera().position.y;
        int lengthY = (int) rendererToolsRegistry.getCamera().viewportHeight;

        int startX = (int) rendererToolsRegistry.getCamera().position.x;
        int lengthX = (int) rendererToolsRegistry.getCamera().viewportWidth;


        for (int i = Math.min(startX + lengthX / 2, dungeon.getWidth() - 1); i >= Math.max(0, startX - lengthX / 2); i--) {
            for (int j = Math.min(startY + lengthY / 2, dungeon.getHeight() - 1); j >= Math.max(0, startY - lengthY / 2); j--) {
                WorldObject worldObject = objectRegistry.getObjectGrid().get(dungeon)[i][j][1];
                if (worldObject != null) {

                    if(River.class.isAssignableFrom(worldObject.getClass())) {
                        spriteBatch.setColor(Color.valueOf("FFFFFFDD"));
                    } else {
                        setColorForObjects(dungeon, spriteBatch, i, j, worldObject);
                    }

                    if (dungeon.getVisitedareaMap()[i][j] != VisitedArea.NOT_VISITED) {
                        // skipping ground objects
                        if (Floor.class.isAssignableFrom(worldObject.getClass())) {
                            continue;
                        }

                        if (TileableObject.class.isAssignableFrom(worldObject.getClass())) {
                            spriteBatch.draw(TileSelector.INSTANCE.getFor(objectRegistry.getObjectGrid().get(dungeon), worldObject, ((TileableObject)worldObject).getClass()), worldObject.getX(), worldObject.getY(), 1, 1);
                        } else if (AnimatedObject.class.isAssignableFrom(worldObject.getClass())) {
                            if (dungeon.getVisitedareaMap()[(int) worldObject.getX()][(int) worldObject.getY()] == VisitedArea.VISIBLE)
                                animationRegistry.get((AnimatedObject) worldObject).drawKeyFrame(spriteBatch, worldObject.getX(), worldObject.getY(), 1, Direction.RIGHT);
                        } else {

                            boolean flipX = false, flipY = false;

                            if (Rotatable.class.isAssignableFrom(worldObject.getClass())) {
                                flipX = ((Rotatable) worldObject).getFlipX();
                                flipY = ((Rotatable) worldObject).getFlipY();
                            }

                            if (Decoration.class.isAssignableFrom(worldObject.getClass())) {
                                spriteBatch.setColor(Color.valueOf("FFFFFF22"));
                                spriteBatch.draw(textureRegistry.getShadowTexture(), worldObject.getX(), worldObject.getY(), 0, 0, 1, 1, worldObject.getWorldMapSize(), worldObject.getWorldMapSize(), 0, 0, 0, 32, 32, false, false);

                                if (dungeon.getVisitedareaMap()[i][j] == VisitedArea.VISITED_BUT_NOT_VISIBLE) {
                                    spriteBatch.setColor(Color.DARK_GRAY);
                                } else {
                                    setColorForObjects(dungeon, spriteBatch, i, j, worldObject);
                                }
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

    private void setColorForObjects(Map2D dungeon, SpriteBatch spriteBatch, int i, int j, WorldObject worldObject) {
        // setting transparency if the view is blocked by decoration
        if (Decoration.class.isAssignableFrom(worldObject.getClass()) && isActorAdjacent(dungeon, i, j)) {
            if(DayTimeCalculator.INSTANCE.isItNight()) {
                spriteBatch.setColor(Config.Engine.NIGHT_COLOR_ALPHA);
            } else if(DayTimeCalculator.INSTANCE.isDawn()) {
                spriteBatch.setColor(Config.Engine.DAWN_COLOR_ALPHA);
            }
            else if(DayTimeCalculator.INSTANCE.isDusk()) {
                spriteBatch.setColor(Config.Engine.DUSK_COLOR_ALPHA);
            }
            else {
                spriteBatch.setColor(Color.valueOf("FFFFFF88"));
            }
        } else {
            if(DayTimeCalculator.INSTANCE.isItNight()) {
                spriteBatch.setColor(Config.Engine.NIGHT_COLOR);
            } else if(DayTimeCalculator.INSTANCE.isDawn()) {
                spriteBatch.setColor(Config.Engine.DAWN_COLOR);
            }
            else if(DayTimeCalculator.INSTANCE.isDusk()) {
                spriteBatch.setColor(Config.Engine.DUSK_COLOR);
            }
            else {
                spriteBatch.setColor(Color.WHITE);
            }
        }
    }

    private boolean isActorAdjacent(Map2D dungeon, int x, int y) {

        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {

                if(x + i >= dungeon.getWidth() || y + j >= dungeon.getHeight() || x + i < 0 || y + j < 0) {
                    continue;
                }

                if(ActorRegistry.INSTANCE.getActorGrid().get(dungeon)[x + i][y + j] != null) {
                    return true;
                }
            }
        }

        return false;
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
