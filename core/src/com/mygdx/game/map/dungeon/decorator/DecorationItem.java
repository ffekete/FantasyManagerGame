package com.mygdx.game.map.dungeon.decorator;

import com.mygdx.game.map.Map2D;

public interface DecorationItem {

    boolean place(Map2D map, int mask, int x, int y);

}
