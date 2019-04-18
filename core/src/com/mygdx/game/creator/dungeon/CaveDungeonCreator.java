package com.mygdx.game.creator.dungeon;

import com.mygdx.game.Config;
import com.mygdx.game.dto.Dungeon;
import com.sun.javafx.image.IntPixelGetter;

import java.util.Random;

public class CaveDungeonCreator implements DungeonCreator {

    public static final int FLOOR_VALUE = 1;
    public static final int EMPTY_SPACE = 0;
    public static final int WALL_VALUE = 2;
    public static final int FILL_VALUE = 4;
    private int deathLimit = 4;
    private int birthLimit = 4;
    private int numberOfSteps = 3;
    private float chanceToStartAlive = 50;

    @Override
    public Dungeon create() {

        long start = System.currentTimeMillis();
        //Create a new map
        Dungeon cellmap = new Dungeon(Config.DungeonConfig.DUNGEON_WIDTH, Config.DungeonConfig.DUNGEON_HEIGHT);
        //Set up the map with random values
        cellmap = initialiseMap(cellmap);

        //And now run the simulation for a set number of steps
        for(int i=0; i<numberOfSteps; i++){
            cellmap = doSimulationStep(cellmap);
        }

        addFrame(cellmap);

        int[] coord = findFirstFloor(cellmap);
        if(coord == null) return null;
        //int allArea = countTraversableArea(cellmap, EMPTY_SPACE);
        //fill(coord[0],coord[1], cellmap, FILL_VALUE);
        //int traversable = countTraversableArea(cellmap, FILL_VALUE);

        //System.out.println(1.0f * traversable / allArea * 100.f);
        System.out.println("Elapsed: " + (System.currentTimeMillis() - start) + " ms");
        return cellmap;
    }

    private int countTraversableArea(Dungeon map, int value) {
        int count = 0;

        for(int i = 0; i < map.getWidth(); i++) {
            for(int j = 0; j < map.getHeight(); j++) {
                if (map.getTile(i,j) == value) count++;
            }
        }
        return count;
    }

    private int[] findFirstFloor(Dungeon map) {
        for(int i = 0; i < map.getWidth(); i++) {
            for(int j = 0; j < map.getHeight(); j++) {
                if (map.getTile(i,j) == FLOOR_VALUE) return new int[] {i,j};
            }
        }
        return null;
    }

    private void addFrame(Dungeon map) {
        for(int i = 0; i < Config.DungeonConfig.DUNGEON_WIDTH; i++) {
            map.setTile(i, 0, WALL_VALUE);
            map.setTile(i, Config.DungeonConfig.DUNGEON_HEIGHT - 1, WALL_VALUE);
        }

        for(int i = 0; i < Config.DungeonConfig.DUNGEON_HEIGHT; i++) {
            map.setTile(0, i, WALL_VALUE);
            map.setTile(Config.DungeonConfig.DUNGEON_WIDTH - 1, i, WALL_VALUE);
        }

    }

    private Dungeon initialiseMap(Dungeon map){
        for(int x = 0; x< Config.DungeonConfig.DUNGEON_WIDTH; x++){
            for(int y = 0; y< Config.DungeonConfig.DUNGEON_HEIGHT; y++){
                if(new Random().nextInt(100) < chanceToStartAlive){
                    map.setTile(x, y, WALL_VALUE);
                }
            }
        }
        return map;
    }

    private int countAliveNeighbours(Dungeon map, int x, int y){
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
                else if(neighbour_x < 0 || neighbour_y < 0 || neighbour_x >= map.getWidth() || neighbour_y >= map.getHeight()){
                    count = count + 1;
                }
                //Otherwise, a normal check of the neighbour
                else if(map.getTile(neighbour_x, neighbour_y) == WALL_VALUE){
                    count = count + 1;
                }
            }
        }
        return count;
    }

    private Dungeon doSimulationStep(Dungeon oldMap){
        Dungeon newMap = new Dungeon(Config.DungeonConfig.DUNGEON_WIDTH, Config.DungeonConfig.DUNGEON_HEIGHT);
        //Loop over each row and column of the map
        for(int x=1; x<oldMap.getWidth() - 1; x++){
            for(int y=1; y<oldMap.getHeight()-1; y++){
                int nbs = countAliveNeighbours(oldMap, x, y);
                //The new value is based on our simulation rules
                //First, if a cell is alive but has too few neighbours, kill it.
                if(oldMap.getTile(x, y) == 1){
                    if(nbs < deathLimit){
                        newMap.setTile(x, y, FLOOR_VALUE);
                    }
                    else{
                        newMap.setTile(x, y, WALL_VALUE);
                    }
                } //Otherwise, if the cell is dead now, check if it has the right number of neighbours to be 'born'
                else{
                    if(nbs > birthLimit){
                        newMap.setTile(x, y, WALL_VALUE);
                    }
                    else{
                        newMap.setTile(x, y, FLOOR_VALUE);
                    }
                }
            }
        }
        return newMap;
    }

    private void fill(int x, int y, Dungeon oldmap, int value) {
        if(x >= oldmap.getWidth() || y >= oldmap.getHeight() || x < 0 || y < 0 || oldmap.getTile(x, y) == 1 || oldmap.getTile(x,y) == 2) {
            return;
        }

        oldmap.setTile(x, y, value);

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
