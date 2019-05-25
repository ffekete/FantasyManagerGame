package com.mygdx.game.map;

public class World {

    private Integer[][] tiles;

    public World(Integer[][] tiles) {
        this.tiles = tiles;
    }

    public Integer getTiles(int x,int y) {
        return tiles[x][y];
    }
}
