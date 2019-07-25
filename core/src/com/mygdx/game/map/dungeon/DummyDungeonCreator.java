package com.mygdx.game.map.dungeon;

public class DummyDungeonCreator implements DungeonGenerator {

    @Override
    public Dungeon create(int steps) {
        Dungeon dungeon = new Dungeon(100, 100, DungeonType.CAVE);
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                dungeon.setTile(i,j, Tile.FLOOR);
            }
        }
        return dungeon;
    }
}
