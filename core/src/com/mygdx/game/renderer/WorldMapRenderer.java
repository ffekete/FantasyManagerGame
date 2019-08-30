package com.mygdx.game.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Config;
import com.mygdx.game.logic.time.DayTimeCalculator;
import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.logic.visibility.VisitedArea;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.worldmap.WorldMapTile;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;
import com.mygdx.game.renderer.camera.CameraPositionController;
import com.mygdx.game.renderer.selector.DirtTileSelector;

public class WorldMapRenderer implements Renderer<Map2D> {

    public static final WorldMapRenderer INSTANCE = new WorldMapRenderer();

    private final TextureRegistry textureRegistry = TextureRegistry.INSTANCE;
    private final CameraPositionController cameraPositionController = CameraPositionController.INSTANCE;
    private final DirtTileSelector dirtTileSelector = new DirtTileSelector();

    private TextureRegion texture;
    private TextureRegion localtextureRegion = new TextureRegion();

    private VisibilityMask visibilityMask;

    @Override
    public void draw(Map2D map, SpriteBatch spriteBatch) {

        if (CameraPositionController.INSTANCE.getZoom() > Config.Engine.ZOOM_MAX_TO_SMALL_MAP) {

            localtextureRegion.setRegion(textureRegistry.getForTile(WorldMapTile.GRASS));
            texture = localtextureRegion;

            for (int i = 0; i < map.getWidth(); i += 10) {
                for (int j = 0; j < map.getHeight(); j += 10) {

//                    if(map.getTile(i,j).equals(WorldMapTile.GRASS))
//                        spriteBatch.setColor(Color.GREEN);
//
//                    if(map.getTile(i,j).equals(WorldMapTile.DIRT))
//                        spriteBatch.setColor(Color.BROWN);
//
//                    spriteBatch.draw(texture, i/10, j/10, 1, 1);

                    if (WorldMapTile.class.isAssignableFrom(map.getTile(i, j).getClass()) && ((WorldMapTile) map.getTile(i, j)).isTiled()) {
                        texture = dirtTileSelector.getFor(map, i, j, 10);//textureRegistry.getForTile(map.getTile(i, j))
                    } else {
                        localtextureRegion.setRegion(textureRegistry.getForTile(map.getTile(i, j)));
                        texture = localtextureRegion;
                    }

                    spriteBatch.draw(texture, i / 10, j / 10, 1, 1);
                }
            }

        } else {

            visibilityMask = VisibilityMapRegistry.INSTANCE.getFor(map);

            if (visibilityMask != null)
                visibilityMask.mask(map, map.getVisitedareaMap());

            for (int i = Math.max((int) cameraPositionController.getCameraposition().getX() - 40, 0); i < Math.min((int) cameraPositionController.getCameraposition().getX() + 40, Config.WorldMap.WORLD_WIDTH); i++) {
                for (int j = Math.max((int) cameraPositionController.getCameraposition().getY() - 40, 0); j < Math.min((int) cameraPositionController.getCameraposition().getY() + 40, Config.WorldMap.WORLD_HEIGHT); j++) {

                    if (map.getVisitedareaMap()[i][j] == VisitedArea.VISITED_BUT_NOT_VISIBLE) {
                        spriteBatch.setColor(Color.DARK_GRAY);
                    } else {
                        if (DayTimeCalculator.INSTANCE.isItNight()) {
                            spriteBatch.setColor(Config.Engine.NIGHT_COLOR);
                        } else if (DayTimeCalculator.INSTANCE.isDawn()) {
                            spriteBatch.setColor(Config.Engine.DAWN_COLOR);
                        } else if (DayTimeCalculator.INSTANCE.isDusk()) {
                            spriteBatch.setColor(Config.Engine.DUSK_COLOR);
                        } else {
                            spriteBatch.setColor(Color.WHITE);
                        }
                    }

                    if (map.getVisitedareaMap()[i][j] != VisitedArea.NOT_VISITED) {

                        if (WorldMapTile.class.isAssignableFrom(map.getTile(i, j).getClass()) && ((WorldMapTile) map.getTile(i, j)).isTiled()) {
                            texture = dirtTileSelector.getFor(map, i, j, 1);//textureRegistry.getForTile(map.getTile(i, j))
                        } else {
                            localtextureRegion.setRegion(textureRegistry.getForTile(map.getTile(i, j)));
                            texture = localtextureRegion;
                        }

                        spriteBatch.draw(texture, i, j, 1, 1);
                    }
                }
            }
        }
    }
}
