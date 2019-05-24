package com.mygdx.game.creator.map.dungeon;

import com.mygdx.game.creator.map.Map2D;

public interface MapGenerator {
    Map2D create(int steps);
}
