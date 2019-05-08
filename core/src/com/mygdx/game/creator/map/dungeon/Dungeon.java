package com.mygdx.game.creator.map.dungeon;

import com.mygdx.game.Config;
import com.mygdx.game.creator.TileBase;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.visibility.VisibilityCalculator;
import com.mygdx.game.logic.visibility.VisitedArea;

public class Dungeon implements Map2D {
    private TileBase[][] dungeon;
    private VisitedArea[][] visitedareaMap;
    private final int height;
    private final int width;
    private final VisibilityCalculator visibilityCalculator;
    private final MapType mapType = MapType.DUNGEON;

    public Dungeon(int width, int height) {
        this.height = height;
        this.width = width;

        visibilityCalculator = new VisibilityCalculator(width, height);

        visitedareaMap = new VisitedArea[width][height];
        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                visitedareaMap[i][j] = VisitedArea.NOT_VISITED;
            }
        }
        this.dungeon = new Tile[width][height];
    }

    public TileBase getTile(int x, int y) {
        if(x < 0 || x > width -1 || y < 0 || y > width -1)
            return Tile.EMPTY;
        return dungeon[x][y];
    }

    public void setTile(int x, int y, TileBase value) {
        if(x < 0 || x > width -1 || y < 0 || y > width -1)
            return;

        dungeon[x][y] = value;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public VisitedArea[][] getVisitedareaMap() {
        return visitedareaMap;
    }

    @Override
    public void setVisitedAreaMap(VisitedArea[][] map) {
        this.visitedareaMap = map;
    }

    @Override
    public VisibilityCalculator getVisibilityCalculator() {
        return visibilityCalculator;
    }

    @Override
    public boolean isExplored() {
        int visited = 0;
        int unvisited = 0;

        for (int i = 0; i < visitedareaMap.length; i++) {
            for (int j = 0; j < visitedareaMap[0].length; j++) {
                if(!getTile(i,j).isObstacle()) {
                    if(visitedareaMap[i][j].equals(VisitedArea.VISIBLE) || visitedareaMap[i][j].equals(VisitedArea.VISITED_BUT_NOT_VISIBLE)) {
                        visited++;
                    } else {
                        unvisited++;
                    }
                }
            }
        }
        return unvisited < Config.Dungeon.VISITED_AREA_THRESHOLD;
    }

    @Override
    public Point getDefaultSpawnPoint() {
        return null;
    }

    @Override
    public MapType getMapType() {
        return mapType;
    }
}
