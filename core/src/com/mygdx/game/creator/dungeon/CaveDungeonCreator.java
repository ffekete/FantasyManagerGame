package com.mygdx.game.creator.dungeon;

import com.mygdx.game.Config;

import java.util.Random;

public class CaveDungeonCreator implements DungeonCreator {

    private int deathLimit = 4;
    private int birthLimit = 4;
    private int numberOfSteps = 3;
    private float chanceToStartAlive = 50;

    @Override
    public int[][] create() {

        long start = System.currentTimeMillis();
        //Create a new map
        int[][] cellmap = new int[Config.DungeonConfig.DUNGEON_WIDTH][Config.DungeonConfig.DUNGEON_HEIGHT];
        //Set up the map with random values
        cellmap = initialiseMap(cellmap);
        //And now run the simulation for a set number of steps
        for(int i=0; i<numberOfSteps; i++){
            cellmap = doSimulationStep(cellmap);
        }

        addFrame(cellmap);

        int[] coord = findFirstFloor(cellmap);
        if(coord == null) return null;
        int allArea = countTraversableArea(cellmap, 0);
        fill(coord[0],coord[1], cellmap, 2);
        int traversable = countTraversableArea(cellmap, 2);

        System.out.println(1.0f * traversable / allArea * 100.f);
        System.out.println("Elapsed: " + (System.currentTimeMillis() - start) + " ms");
        return cellmap;
    }

    private int countTraversableArea(int[][] map, int value) {
        int count = 0;

        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                if (map[i][j] == value) count++;
            }
        }
        return count;
    }

    private int[] findFirstFloor(int[][] map) {
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 0) return new int[] {i,j};
            }
        }
        return null;
    }

    private void addFrame(int[][] map) {
        for(int i = 0; i < Config.DungeonConfig.DUNGEON_WIDTH; i++) {
            map[i][0] = 0;
            map[i][Config.DungeonConfig.DUNGEON_HEIGHT - 1] = 1;
        }

        for(int i = 0; i < Config.DungeonConfig.DUNGEON_HEIGHT; i++) {
            map[0][i] = 0;
            map[Config.DungeonConfig.DUNGEON_WIDTH - 1][i] = 1;
        }

    }

    private int[][] initialiseMap(int[][] map){
        for(int x = 0; x< Config.DungeonConfig.DUNGEON_WIDTH; x++){
            for(int y = 0; y< Config.DungeonConfig.DUNGEON_HEIGHT; y++){
                if(new Random().nextInt(100) < chanceToStartAlive){
                    map[x][y] = 1;
                }
            }
        }
        return map;
    }

    private int countAliveNeighbours(int[][] map, int x, int y){
        int count = 0;
        for(int i=-1; i<2; i++){
            for(int j=-1; j<2; j++){
                int neighbour_x = x+i;
                int neighbour_y = y+j;
                //If we're looking at the middle point
                if(i == 0 && j == 0){
                    //Do nothing, we don't want to add ourselves in!
                }
                //In case the index we're looking at it off the edge of the map
                else if(neighbour_x < 0 || neighbour_y < 0 || neighbour_x >= map.length || neighbour_y >= map[0].length){
                    count = count + 1;
                }
                //Otherwise, a normal check of the neighbour
                else if(map[neighbour_x][neighbour_y] == 1){
                    count = count + 1;
                }
            }
        }
        return count;
    }

    private int[][] doSimulationStep(int[][] oldMap){
        int[][] newMap = new int[Config.DungeonConfig.DUNGEON_WIDTH][Config.DungeonConfig.DUNGEON_WIDTH];
        //Loop over each row and column of the map
        for(int x=1; x<oldMap.length-1; x++){
            for(int y=1; y<oldMap[0].length-1; y++){
                int nbs = countAliveNeighbours(oldMap, x, y);
                //The new value is based on our simulation rules
                //First, if a cell is alive but has too few neighbours, kill it.
                if(oldMap[x][y] == 1){
                    if(nbs < deathLimit){
                        newMap[x][y] = 0;
                    }
                    else{
                        newMap[x][y] = 1;
                    }
                } //Otherwise, if the cell is dead now, check if it has the right number of neighbours to be 'born'
                else{
                    if(nbs > birthLimit){
                        newMap[x][y] = 1;
                    }
                    else{
                        newMap[x][y] = 0;
                    }
                }
            }
        }
        return newMap;
    }

    private void fill(int x, int y, int[][]oldmap, int value) {
        if(x >= oldmap.length || y >= oldmap[0].length || x < 0 || y < 0 || oldmap[x][y] == 1 || oldmap[x][y] == 2) {
            return;
        }

        oldmap[x][y] = value;

        fill(x-1, y, oldmap, value);
        fill(x-1, y-1, oldmap, value);
        fill(x-1, y+1, oldmap, value);
        fill(x, y-1, oldmap, value);
        fill(x, y+1, oldmap, value);
        fill(x+1, y-1, oldmap, value);
        fill(x+1, y, oldmap, value);
        fill(x+1, y+1, oldmap, value);
    }
}
