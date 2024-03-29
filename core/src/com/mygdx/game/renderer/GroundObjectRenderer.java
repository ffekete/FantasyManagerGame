package com.mygdx.game.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Config;
import com.mygdx.game.builder.BuildingBlock;
import com.mygdx.game.logic.time.DayTimeCalculator;
import com.mygdx.game.logic.visibility.VisitedArea;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.floor.DirtRoad;
import com.mygdx.game.object.floor.TileableFloorObject;
import com.mygdx.game.registry.ObjectRegistry;
import com.mygdx.game.registry.RendererToolsRegistry;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.renderer.camera.CameraPositionController;
import com.mygdx.game.renderer.gui.component.GuiComponent;
import com.mygdx.game.renderer.selector.FloorTileSelector;

public class GroundObjectRenderer implements Renderer<Map2D> {

    public static final GroundObjectRenderer INSTANCE = new GroundObjectRenderer();

    private TextureRegion manaBarRegion;

    private final ObjectRegistry objectRegistry = ObjectRegistry.INSTANCE;
    private final TextureRegistry textureRegistry = TextureRegistry.INSTANCE;
    private final RendererToolsRegistry rendererToolsRegistry = RendererToolsRegistry.INSTANCE;

    public GroundObjectRenderer() {
        this.manaBarRegion = new TextureRegion(textureRegistry.getFor(GuiComponent.HUD), 340, 110, 500, 40);
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


        for (int i = Math.max(0, startX - lengthX / 2); i < Math.min(startX + lengthX / 2, dungeon.getWidth() - 1); i++) {
            for (int j = Math.min(startY + lengthY / 2, dungeon.getHeight() - 1); j > Math.max(0, startY - lengthY / 2); j--) {
                WorldObject worldObject = objectRegistry.getObjectGrid().get(dungeon)[i][j][0];
                if (worldObject != null) {

                    if (dungeon.getVisitedareaMap()[i][j] == VisitedArea.VISITED_BUT_NOT_VISIBLE) {
                        spriteBatch.setColor(Color.DARK_GRAY);
                    } else {
                        if (DirtRoad.class.isAssignableFrom(worldObject.getClass())) {
                            spriteBatch.setColor(Color.valueOf("FFFFFFDD"));
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

                    if (dungeon.getVisitedareaMap()[i][j] != VisitedArea.NOT_VISITED) {
                        if (TileableFloorObject.class.isAssignableFrom(worldObject.getClass())) {
                            spriteBatch.draw(FloorTileSelector.INSTANCE.getFor(objectRegistry.getObjectGrid().get(dungeon), worldObject), worldObject.getX(), worldObject.getY(), 1, 1);
                        }

                        if (BuildingBlock.class.isAssignableFrom(worldObject.getClass())) {
                            spriteBatch.draw(manaBarRegion, worldObject.getX() + 0.2f, worldObject.getY() + 1.1f, 1.8f * ((float) ((BuildingBlock) worldObject).getProgress() / 100f), 0.1f);
                        }
                    }
                }
            }
        }
    }
}
