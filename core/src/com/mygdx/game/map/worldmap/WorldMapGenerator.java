package com.mygdx.game.map.worldmap;

import com.mygdx.game.Config;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.MapGenerator;

public class WorldMapGenerator implements MapGenerator {

    @Override
    public WorldMap create(int steps) {

        int width = Config.WorldMap.WORLD_WIDTH;
        int height = Config.WorldMap.WORLD_HEIGHT;

        WorldMap worldMap = new WorldMap(width, height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                worldMap.setTile(i,j, WorldMapTile.GRASS);
            }
        }

        return worldMap;
    }
}
