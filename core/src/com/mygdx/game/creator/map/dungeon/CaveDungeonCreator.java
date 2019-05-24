package com.mygdx.game.creator.map.dungeon;

import com.mygdx.game.Config;
import com.mygdx.game.creator.map.Map2D;

import java.util.Random;

public class CaveDungeonCreator implements MapGenerator {

    private final static boolean DEBUG = false;
    private int deathLimit = 5;
    private int birthLimit = 3;
    private float chanceToStartAlive = 45;

    @Override
    public Map2D create(int steps) {

        long start = System.currentTimeMillis();
        //Create a new map
        Dungeon cellmap = new Dungeon(Config.Dungeon.DUNGEON_WIDTH, Config.Dungeon.DUNGEON_HEIGHT, DungeonType.CAVE);
        //Set up the map with random values
        cellmap = initialiseMap(cellmap);

        //And now update the simulation for a set number of steps
        for(int i=0; i<steps; i++){
            cellmap = doSimulationStep(cellmap);
        }

        addFrame(cellmap);

        int[] coord = findFirstFloor(cellmap);
        if(coord == null) return null;
        if(DEBUG) {
            int allArea = countTraversableArea(cellmap, Tile.FLOOR);
            fill(coord[0],coord[1], cellmap, Tile.FLOOR, Tile.EMPTY);
            int traversable = countTraversableArea(cellmap, Tile.EMPTY);
        }
        System.out.println("Elapsed: " + (System.currentTimeMillis() - start) + " ms");

        return cellmap;
    }

    private int countTraversableArea(Dungeon map, Tile value) {
        int count = 0;

        for(int i = 0; i < map.getWidth(); i++) {
            for(int j = 0; j < map.getHeight(); j++) {
                if (map.getTile(i,j).equals(value)) count++;
            }
        }
        return count;
    }

    private int[] findFirstFloor(Dungeon map) {
        for(int i = 0; i < map.getWidth(); i++) {
            for(int j = 0; j < map.getHeight(); j++) {
                if (map.getTile(i,j).equals(Tile.FLOOR)) return new int[] {i,j};
            }
        }
        return null;
    }

    private void addFrame(Dungeon map) {
        for(int i = 0; i < Config.Dungeon.DUNGEON_WIDTH; i++) {
            map.setTile(i, 0, Tile.STONE_WALL);
            map.setTile(i, Config.Dungeon.DUNGEON_HEIGHT - 1, Tile.STONE_WALL);
        }

        for(int i = 0; i < Config.Dungeon.DUNGEON_HEIGHT; i++) {
            map.setTile(0, i, Tile.STONE_WALL);
            map.setTile(Config.Dungeon.DUNGEON_WIDTH - 1, i, Tile.STONE_WALL);
        }
    }

    private Dungeon initialiseMap(Dungeon map){
        for(int x = 0; x< Config.Dungeon.DUNGEON_WIDTH; x++){
            for(int y = 0; y< Config.Dungeon.DUNGEON_HEIGHT; y++){
                if(new Random().nextInt(100) < chanceToStartAlive){
                    map.setTile(x, y, Tile.STONE_WALL);
                } else {
                    map.setTile(x,y,Tile.FLOOR);
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
                else if(map.getTile(neighbour_x, neighbour_y) == Tile.STONE_WALL){
                    count = count + 1;
                }
            }
        }
        return count;
    }

    private Dungeon doSimulationStep(Dungeon oldMap){
        Dungeon newMap = new Dungeon(Config.Dungeon.DUNGEON_WIDTH, Config.Dungeon.DUNGEON_HEIGHT, DungeonType.CAVE);
        //Loop over each row and column of the map
        for(int x=1; x<oldMap.getWidth() - 1; x++){
            for(int y=1; y<oldMap.getHeight()-1; y++){
                int nbs = countAliveNeighbours(oldMap, x, y);
                //The new value is based on our simulation rules
                //First, if a cell is alive but has too few neighbours, kill it.
                if(oldMap.getTile(x, y).equals(Tile.FLOOR)){
                    if(nbs < deathLimit){
                        newMap.setTile(x, y, Tile.FLOOR);
                    }
                    else{
                        newMap.setTile(x, y, Tile.STONE_WALL);
                    }
                } //Otherwise, if the cell is dead now, check if it has the right number of neighbours to be 'born'
                else{
                    if(nbs > birthLimit){
                        newMap.setTile(x, y, Tile.STONE_WALL);
                    }
                    else{
                        newMap.setTile(x, y, Tile.FLOOR);
                    }
                }
            }
        }
        return newMap;
    }

    private void fill(int x, int y, Dungeon oldmap, Tile originalValue, Tile value) {
        if(x >= oldmap.getWidth() || y >= oldmap.getHeight() || x < 0 || y < 0 || oldmap.getTile(x, y) != originalValue || oldmap.getTile(x,y) == value) {
            return;
        }

        oldmap.setTile(x, y, value);

        fill(x-1, y, oldmap, originalValue, value);
        fill(x-1, y-1, oldmap, originalValue, value);
        fill(x-1, y+1, oldmap, originalValue, value);
        fill(x, y-1, oldmap, originalValue, value);
        fill(x, y+1, oldmap, originalValue, value);
        fill(x+1, y-1, oldmap, originalValue, value);
        fill(x+1, y, oldmap, originalValue, value);
        fill(x+1, y+1, oldmap, originalValue, value);
    }
}
