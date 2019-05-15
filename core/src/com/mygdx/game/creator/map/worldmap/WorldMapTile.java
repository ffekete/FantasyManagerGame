package com.mygdx.game.creator.map.worldmap;

import com.mygdx.game.creator.TileBase;

public enum WorldMapTile implements TileBase {
    EMPTY(true, 1),
    GRASS(false, 1);

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
