package com.mygdx.game.map.dungeon;

import com.mygdx.game.map.Map2D;

public class DummyDungeonCreator implements MapGenerator {

    @Override
    public Map2D create(int steps) {
        Dungeon dungeon = new Dungeon(100, 100, DungeonType.CAVE);
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                dungeon.setTile(i,j, Tile.FLOOR);
            }
        }
        return dungeon;
    }
}