package com.mygdx.game.creator.map.dungeon;

import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.creator.map.Tile;

public class Dungeon implements Map2D {
    private Tile[][] dungeon;
    private final int height;
    private final int width;

    public Dungeon(int width, int height) {
        this.height = height;
        this.width = width;

        this.dungeon = new Tile[width][height];
    }

    public Tile getTile(int x, int y) {
        if(x < 0 || x > width -1 || y < 0 || y > width -1)
            return Tile.EMPTY;
        return dungeon[x][y];
    }

    public void setTile(int x, int y, Tile value) {
        if(x < 0 || x > width -1 || y < 0 || y > width -1)
            return;

        dungeon[x][y] = value;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
