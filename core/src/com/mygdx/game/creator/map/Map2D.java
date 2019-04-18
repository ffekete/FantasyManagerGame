package com.mygdx.game.creator.map;

public interface Map2D {

    int getTile(int x, int y);
    void setTile(int x, int y, int value);
    int getHeight();
    int getWidth();
}
