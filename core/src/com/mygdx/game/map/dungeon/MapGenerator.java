package com.mygdx.game.map.dungeon;

import com.mygdx.game.map.Map2D;

public interface MapGenerator<T extends Map2D> {
    T create(int steps);
}
