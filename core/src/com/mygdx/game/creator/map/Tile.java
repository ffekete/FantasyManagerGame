package com.mygdx.game.creator.map;

import com.mygdx.game.creator.TileBase;

public enum Tile implements TileBase {

    EMPTY(false),
    FLOOR(false),
    STONE_WALL(true);

    Tile(boolean obstacle) {
        this.obstacle = obstacle;
    }

    private boolean obstacle;

    @Override
    public boolean isObstacle() {
        return obstacle;
    }
}
