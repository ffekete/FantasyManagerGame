package com.mygdx.game.creator.map.dungeon;

import com.mygdx.game.creator.map.Map2D;

public class Dungeon implements Map2D {
    private int[][] dungeon;
    private final int height;
    private final int width;

    public Dungeon(int width, int height) {
        this.height = height;
        this.width = width;

        this.dungeon = new int[width][height];
    }

    public int getTile(int x, int y) {
        if(x < 0 || x > width -1 || y < 0 || y > width -1)
            return 0;
        return dungeon[x][y];
    }

    public void setTile(int x, int y, int value) {
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
