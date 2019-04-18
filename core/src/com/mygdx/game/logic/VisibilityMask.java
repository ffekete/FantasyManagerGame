package com.mygdx.game.logic;

import com.mygdx.game.creator.map.dungeon.Dungeon;

public class VisibilityMask {

    private static final int DEFAULT_VALE = 0;

    private final int width;
    private final int height;

    private int[][] mask;

    public VisibilityMask(int width, int height) {
        this.width = width;
        this.height = height;
        mask = new int[width][height];
    }

    public int[][] mask(Dungeon map) {
        if(width != map.getWidth() || height != map.getHeight())
            throw new IllegalArgumentException("Map sizes are not matching with mask!");
        int[][] newMap = new int[map.getWidth()][map.getHeight()];

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                newMap[i][j] = mask[i][j] == 0 ? 0 : map.getTile(i,j);
            }
        }

        return newMap;
    }

    public void reset() {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                mask[i][j] = DEFAULT_VALE;
            }
        }
    }

    public void setValue(int x, int y) {
        mask[x][y] = 1;
    }

    public int getValue(int x, int y) {
        return mask[x][y];
    }
}
