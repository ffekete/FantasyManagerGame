package com.mygdx.game.logic;

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

    public void reset() {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                mask[i][j] = DEFAULT_VALE;
            }
        }
    }

    public int getValue(int x, int y) {
        return mask[x][y];
    }
}
