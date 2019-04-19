package com.mygdx.game.creator.map.dungeon;

import com.mygdx.game.creator.map.Tile;

public class DummyDungeonCreator implements DungeonCreator {

    @Override
    public Dungeon create() {
        Dungeon dungeon = new Dungeon(100, 100);
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                dungeon.setTile(i,j, Tile.FLOOR);
            }
        }
        return dungeon;
    }
}
