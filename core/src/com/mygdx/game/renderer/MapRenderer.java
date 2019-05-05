package com.mygdx.game.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Config;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.creator.map.dungeon.Tile;
import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.logic.visibility.VisitedArea;
import com.mygdx.game.registry.VisibilityMapRegistry;

public class MapRenderer  implements Renderer {

    public static final MapRenderer INSTANCE = new MapRenderer();

    private Texture wallTexture = new Texture(Gdx.files.internal("wall.jpg"));
    private Texture floorTexture = new Texture(Gdx.files.internal("terrain.jpg"));

    @Override
    public void draw(Map2D dungeon, SpriteBatch spriteBatch) {

        VisibilityMask visibilityMask = VisibilityMapRegistry.INSTANCE.getFor(dungeon);

        Tile[][] drawMap = visibilityMask.mask(dungeon, dungeon.getVisitedareaMap());

        for (int i = 0; i < Config.Dungeon.DUNGEON_WIDTH; i++) {
            for (int j = 0; j < Config.Dungeon.DUNGEON_HEIGHT; j++) {

                if(dungeon.getVisitedareaMap()[i][j] == VisitedArea.VISITED_BUT_NOT_VISIBLE) {
                    spriteBatch.setColor(Color.DARK_GRAY);
                } else {
                    spriteBatch.setColor(Color.WHITE);
                }
                if (drawMap[i][j].equals(Tile.STONE_WALL)) {
                    spriteBatch.draw(wallTexture, i, j, 0, 0, 1, 1, 1, 1, 0, 0, 0, wallTexture.getWidth(), wallTexture.getHeight(), false, false);
                } else if (drawMap[i][j].equals(Tile.FLOOR)) {
                    spriteBatch.draw(floorTexture, i, j, 0, 0, 1, 1, 1, 1, 0, 0, 0, floorTexture.getWidth(), floorTexture.getHeight(), false, false);
                }
            }
        }
    }
}
