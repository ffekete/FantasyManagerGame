package com.mygdx.game.map.dungeon;

import com.mygdx.game.Config;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.map.TileBase;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.visibility.VisibilityCalculator;
import com.mygdx.game.logic.visibility.VisitedArea;

import java.util.Random;

public class Dungeon implements Map2D {

    private TileBase[][] dungeon;
    private int[][] tileVariation;
    private VisitedArea[][] visitedareaMap;
    private final int height;
    private final int width;
    private final VisibilityCalculator visibilityCalculator;
    private final MapType mapType = MapType.DUNGEON_CAVE;
    private Point defaultSpawningPoint;
    private final DungeonType dungeonType;
    private Dungeon nextLevel = null;
    private Dungeon previousLevel = null;
    private boolean[][] obstacle;
    private float traverseCost[][];

    public Dungeon(int width, int height, DungeonType dungeonType) {
        this.height = height;
        this.width = width;
        this.dungeonType = dungeonType;

        obstacle = new boolean[width][height];

        visibilityCalculator = new VisibilityCalculator(width, height);

        this.traverseCost = new float[width][height];

        visitedareaMap = new VisitedArea[width][height];
        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                visitedareaMap[i][j] = VisitedArea.NOT_VISITED;
                traverseCost[i][j] = PathFinder.DEFAULT_EFFORT;
            }
        }
        this.dungeon = new Tile[width][height];
        this.tileVariation = new int[width][height];
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
        int variation = new Random().nextInt(value.getVariation() * Config.Dungeon.TILE_VARIATION_FREQUENCY);
        if(variation >= value.getVariation())
            variation = 0;
        tileVariation[x][y] = variation;
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
                if(!obstacle[i][j] && !getTile(i,j).isObstacle()) {
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
        Dungeon topLevel = this;
        while (topLevel.previousLevel != null) {
            topLevel = topLevel.previousLevel;
        }

        while(topLevel != null) {
            if (!topLevel.isExplored())
                return false;

            topLevel = topLevel.nextLevel;
        }
        return true;
    }

    @Override
    public Point getDefaultSpawnPoint() {
        return defaultSpawningPoint;
    }

    @Override
    public void setDefaultSpawningPoint(Point point) {
        defaultSpawningPoint = point;
    }

    @Override
    public MapType getMapType() {
        return mapType;
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
    public int getTileVariation(int x, int y) {
        return tileVariation[x][y];
    }

    @Override
    public float getTraverseCost(int x, int y) {
        return traverseCost[x][y];
    }

    @Override
    public void setTraverseCost(int x, int y, float val) {
        this.traverseCost[x][y] = val;
    }

    public DungeonType getDungeonType() {
        return dungeonType;
    }

    public void setNextLevel(Dungeon nextLevel) {
        this.nextLevel = nextLevel;
    }

    public void setPreviousLevel(Dungeon previousLevel) {
        this.previousLevel = previousLevel;
    }

    public Dungeon getNextLevel() {
        return nextLevel;
    }

    public Dungeon getPreviousLevel() {
        return previousLevel;
    }
}
