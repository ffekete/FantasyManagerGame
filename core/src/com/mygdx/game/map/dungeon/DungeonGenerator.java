package com.mygdx.game.map.dungeon;

public interface DungeonGenerator extends MapGenerator {

    @Override
    Dungeon create(int steps);
}
