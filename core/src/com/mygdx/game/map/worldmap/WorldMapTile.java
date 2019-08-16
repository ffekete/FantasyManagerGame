package com.mygdx.game.map.worldmap;

import com.mygdx.game.map.TileBase;

public enum WorldMapTile implements TileBase {
    EMPTY(true, false,  1),
    GRASS(false, false, 1),
    WOODEN_WALL(true, false, 1),
    DIRT(false, true, 1),
    WATER(true, true, 1);

    private boolean obstacle;
    private boolean tiled;
    private int variation;

    WorldMapTile(boolean obstacle, boolean tiled, int variation) {
        this.obstacle = obstacle;
        this.variation = variation;
        this.tiled = tiled;
    }

    @Override
    public boolean isObstacle() {
        return obstacle;
    }

    @Override
    public int getVariation() {
        return variation;
    }

    public boolean isTiled() {
        return tiled;
    }
}
