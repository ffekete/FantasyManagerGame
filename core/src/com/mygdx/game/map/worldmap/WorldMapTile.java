package com.mygdx.game.map.worldmap;

import com.mygdx.game.map.TileBase;

public enum WorldMapTile implements TileBase {
    EMPTY(true, 1),
    GRASS(false, 1),
    WOODEN_WALL(true, 1);

    private boolean obstacle;
    private int variation;

    WorldMapTile(boolean obstacle, int variation) {
        this.obstacle = obstacle;
        this.variation = variation;
    }

    @Override
    public boolean isObstacle() {
        return obstacle;
    }

    @Override
    public int getVariation() {
        return variation;
    }
}
