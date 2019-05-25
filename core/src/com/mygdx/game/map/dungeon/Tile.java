package com.mygdx.game.map.dungeon;

import com.mygdx.game.map.TileBase;

public enum Tile implements TileBase {

    EMPTY(false, 1),
    FLOOR(false, 9),
    STONE_WALL(true, 1);

    Tile(boolean obstacle, int variation) {
        this.obstacle = obstacle;
        this.variation = variation;
    }

    private boolean obstacle;
    private int variation;

    @Override
    public boolean isObstacle() {
        return obstacle;
    }

    @Override
    public int getVariation() {
        return variation;
    }
}
