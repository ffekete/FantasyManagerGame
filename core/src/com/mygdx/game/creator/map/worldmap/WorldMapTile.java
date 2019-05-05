package com.mygdx.game.creator.map.worldmap;

import com.mygdx.game.creator.TileBase;

public enum WorldMapTile implements TileBase {
    EMPTY(true),
    GRASS(false);

    private boolean obstacle;

    WorldMapTile(boolean obstacle) {
        this.obstacle = obstacle;
    }

    @Override
    public boolean isObstacle() {
        return obstacle;
    }
}
