package com.mygdx.game.map.worldmap;

import com.mygdx.game.Config;

import java.util.Random;

public class WorldMapDirtSpreadDecorator {

    private final static boolean DEBUG = false;

    private int deathLimit = 5;
    private int birthLimit = 3;
    private float chanceToStartAlive = 45;


    public void decorate(int step, WorldMap worldMap) {

        WorldMap newMap = create(step);

        for (int i = 0; i < newMap.getWidth(); i++) {
            for (int j = 0; j < newMap.getHeight(); j++) {
                if (newMap.getTile(i, j) != null) {
                    worldMap.setTile(i, j, newMap.getTile(i, j));
                }
            }
        }
    }

    public WorldMap create(int steps) {

        long start = System.currentTimeMillis();
        //Create a new map
        WorldMap cellmap = new WorldMap(Config.WorldMap.WORLD_WIDTH, Config.WorldMap.WORLD_HEIGHT);
        //Set up the map with random values
        cellmap = initialiseMap(cellmap);

        //And now update the simulation for a set number of steps
        for (int i = 0; i < steps; i++) {
            cellmap = doSimulationStep(cellmap);
        }

        System.out.println("Elapsed: " + (System.currentTimeMillis() - start) + " ms");

        return cellmap;
    }

    private WorldMap initialiseMap(WorldMap map) {
        for (int x = 0; x < Config.WorldMap.WORLD_WIDTH; x++) {
            for (int y = 0; y < Config.WorldMap.WORLD_HEIGHT; y++) {
                if (new Random().nextInt(100) < chanceToStartAlive) {
                    map.setTile(x, y, WorldMapTile.DIRT);
                } else {
                    map.setTile(x, y, WorldMapTile.GRASS);
                }
            }
        }
        return map;
    }

    private int countAliveNeighbours(WorldMap map, int x, int y) {
        int count = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int neighbour_x = x + i;
                int neighbour_y = y + j;
                //If we're looking at the middle point
                if (i == 0 && j == 0) {
                    //Do nothing, we don't want to add ourselves in!
                }
                //In case the index we're looking at it off the edge of the map
                else if (neighbour_x < 0 || neighbour_y < 0 || neighbour_x >= map.getWidth() || neighbour_y >= map.getHeight()) {
                    count = count + 1;
                }
                //Otherwise, a normal check of the neighbour
                else if (map.getTile(neighbour_x, neighbour_y) == WorldMapTile.DIRT) {
                    count = count + 1;
                }
            }
        }
        return count;
    }

    private WorldMap doSimulationStep(WorldMap oldMap) {
        WorldMap newMap = new WorldMap(Config.WorldMap.WORLD_WIDTH, Config.WorldMap.WORLD_HEIGHT);
        //Loop over each row and column of the map
        for (int x = 1; x < oldMap.getWidth() - 1; x++) {
            for (int y = 1; y < oldMap.getHeight() - 1; y++) {
                int nbs = countAliveNeighbours(oldMap, x, y);
                //The new value is based on our simulation rules
                //First, if a cell is alive but has too few neighbours, kill it.
                if (oldMap.getTile(x, y).equals(WorldMapTile.GRASS)) {
                    if (nbs < deathLimit) {
                        newMap.setTile(x, y, WorldMapTile.GRASS);
                    } else {
                        newMap.setTile(x, y, WorldMapTile.DIRT);
                    }
                } //Otherwise, if the cell is dead now, check if it has the right number of neighbours to be 'born'
                else {
                    if (nbs > birthLimit) {
                        newMap.setTile(x, y, WorldMapTile.DIRT);
                    } else {
                        newMap.setTile(x, y, WorldMapTile.GRASS);
                    }
                }
            }
        }
        return newMap;
    }
}
