package com.mygdx.game.creator.map.worldmap;

import com.mygdx.game.Config;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.creator.map.dungeon.Dungeon;
import com.mygdx.game.creator.map.dungeon.MapGenerator;

import java.util.Random;

public class WorldMapGenerator implements MapGenerator {

    @Override
    public Map2D create() {

        int width = Config.WorldMap.WORLD_WIDTH;
        int height = Config.WorldMap.WORLD_HEIGHT;

        Map2D worldMap = new WorldMap(width, height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                worldMap.setTile(i,j, WorldMapTile.GRASS);
            }
        }

        return worldMap;
    }
}
