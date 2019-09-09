package com.mygdx.game.map.worldmap;

import com.mygdx.game.Config;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.logic.visibility.VisibilityCalculator;
import com.mygdx.game.logic.visibility.VisitedArea;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.TileBase;

import java.util.Random;

public class WorldMap implements Map2D {
    private TileBase[][] worldMap;
    private float traverseCost[][];
    private VisitedArea[][] visitedareaMap;
    private final int height;
    private final int width;
    private final VisibilityCalculator visibilityCalculator;
    private final MapType mapType = MapType.WORLD_MAP;
    private Point defaultSpawningPoint;
    private int[][] tileVariation;
    private boolean[][] obstacle;
    private boolean[][] noViewBlockingObstacle;

    public WorldMap(int width, int height) {
        this.height = height;
        this.width = width;

        visibilityCalculator = new VisibilityCalculator(width, height);

        obstacle = new boolean[width][height];
        noViewBlockingObstacle = new boolean[width][height];
        traverseCost = new float[width][height];

        visitedareaMap = new VisitedArea[width][height];
        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                visitedareaMap[i][j] = VisitedArea.NOT_VISITED;
                obstacle[i][j] = false;
                noViewBlockingObstacle[i][j] = false;
                traverseCost[i][j] = PathFinder.DEFAULT_EFFORT;
            }
        }
        this.worldMap = new WorldMapTile[width][height];
        this.tileVariation = new int[width][height];
    }

    public TileBase getTile(int x, int y) {
        if(x < 0 || x > width -1 || y < 0 || y > width -1)
            return WorldMapTile.EMPTY;
        return worldMap[x][y];
    }

    public void setTile(int x, int y, TileBase value) {
        if(x < 0 || x > width -1 || y < 0 || y > width -1)
            return;

        worldMap[x][y] = value;
        tileVariation[x][y] = new Random().nextInt(value.getVariation());
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
    public boolean isObstacle(int x, int y) {
        return obstacle[x][y];
    }

    @Override
    public void setObstacle(int x, int y, boolean value) {
        obstacle[x][y] = value;
    }

    @Override
    public boolean isNoViewBlockingObstacle(int x, int y) {
        return noViewBlockingObstacle[x][y];
    }

    @Override
    public void setNoViewBlockingObstacle(int x, int y, boolean value) {
        noViewBlockingObstacle[x][y] = value;
    }

    @Override
    public boolean isExplored() {
        int visited = 0;
        int unvisited = 0;

        for (int i = 0; i < visitedareaMap.length; i++) {
            for (int j = 0; j < visitedareaMap[0].length; j++) {
                if(!obstacle[i][j]) {
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
    public boolean areAllLevelsExplored() {
        return isExplored();
    }

    @Override
    public Point getDefaultSpawnPoint() {
        return defaultSpawningPoint;
    }

    @Override
    public void setDefaultSpawningPoint(Point point) {
        this.defaultSpawningPoint = point;
    }

    @Override
    public MapType getMapType() {
        return mapType;
    }

    @Override
    public int getTileVariation(int x, int y) {
        return 0;
    }

    @Override
    public float getTraverseCost(int x, int y) {
        return traverseCost[x][y];
    }

    @Override
    public void setTraverseCost(int x, int y, float val) {
        this.traverseCost[x][y] = val;
    }
}
