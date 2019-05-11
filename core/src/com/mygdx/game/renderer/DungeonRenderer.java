package com.mygdx.game.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Config;
import com.mygdx.game.creator.TileBase;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.logic.visibility.VisitedArea;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;

public class DungeonRenderer implements Renderer {

    public static final DungeonRenderer INSTANCE = new DungeonRenderer();

    private final TextureRegistry textureRegistry = TextureRegistry.INSTANCE;

    @Override
    public void draw(Map2D map, SpriteBatch spriteBatch) {

        VisibilityMask visibilityMask = VisibilityMapRegistry.INSTANCE.getFor(map);

        // if nothing to calculate for now
        if(visibilityMask == null)
            return;

        visibilityMask.mask(map, map.getVisitedareaMap());

        for (int i = 0; i < Config.Dungeon.DUNGEON_WIDTH; i++) {
            for (int j = 0; j < Config.Dungeon.DUNGEON_HEIGHT; j++) {

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
