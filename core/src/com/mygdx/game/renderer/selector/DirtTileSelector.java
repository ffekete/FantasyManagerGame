package com.mygdx.game.renderer.selector;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.worldmap.WorldMap;
import com.mygdx.game.map.worldmap.WorldMapTile;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.utils.MapUtils;

public class DirtTileSelector {

    private final TextureRegistry textureRegistry = TextureRegistry.INSTANCE;
    private TextureRegion textureRegion;

    public DirtTileSelector() {
        textureRegion = new TextureRegion(textureRegistry.getForTile(WorldMapTile.DIRT));
    }

    public TextureRegion getFor(Map2D map, int x, int y, int skip) {

        int mask = MapUtils.bitmask4bitForTile((WorldMap) map, x,y, WorldMapTile.DIRT, skip);

        // rock
        textureRegion.setRegion((mask % 4) * 16, (mask / 4) * 16, 16, 16);
        return textureRegion;
    }

}
