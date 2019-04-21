package com.mygdx.game.logic.visibility;

import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.creator.map.Tile;
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

    public Tile[][] mask(Map2D map, VisitedArea[][] visitedAreaMap) {
        if(width != map.getWidth() || height != map.getHeight())
            throw new IllegalArgumentException("Map sizes are not matching with mask!");
        Tile[][] newMap = new Tile[map.getWidth()][map.getHeight()];

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                if(mask[i][j] == 0) {
                    if(visitedAreaMap[i][j] == VisitedArea.NOT_VISITED) {
                        newMap[i][j] = Tile.EMPTY;
                    } else {
                        visitedAreaMap[i][j] = VisitedArea.VISITED_BUT_NOT_VISIBLE; // visited but not seen
                        newMap[i][j] = map.getTile(i,j);
                    }
                } else {
                    visitedAreaMap[i][j] = VisitedArea.VISIBLE; // visited and seen
                    newMap[i][j] = map.getTile(i,j);
                }
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
