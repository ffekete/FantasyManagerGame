package com.mygdx.game.renderer.selector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.DungeonType;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.utils.MapUtils;
public class CaveTileSelector {

    private final TextureRegistry textureRegistry = TextureRegistry.INSTANCE;
    private TextureRegion textureRegion;

    public CaveTileSelector() {
        textureRegion = new TextureRegion(textureRegistry.getFor(DungeonType.CAVE));
    }

    public TextureRegion getFor(Map2D map, int x, int y) {

        if (!map.getTile(x, y).isObstacle()) {

            textureRegion.setRegion(64 + (map.getTileVariation(x,y) % 4)* 16, 0 + (map.getTileVariation(x,y) / 4) * 16, 16, 16);
            return textureRegion;
        }

        int mask = MapUtils.bitmask4bit(map, x,y);

        // rock
        textureRegion.setRegion((mask % 4) * 16, (mask / 4) * 16, 16, 16);
        return textureRegion;
    }

}
