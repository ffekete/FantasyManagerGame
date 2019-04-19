package com.mygdx.game.creator.map;

public interface Map2D {

    Tile getTile(int x, int y);
    void setTile(int x, int y, Tile value);
    int getHeight();
    int getWidth();
}
