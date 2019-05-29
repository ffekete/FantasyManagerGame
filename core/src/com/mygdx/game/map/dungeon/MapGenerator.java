package com.mygdx.game.map.dungeon;

import com.mygdx.game.map.Map2D;

public interface MapGenerator {
    Map2D create(int steps);
}
