package com.mygdx.game.map.worldmap;

import com.google.common.collect.ImmutableList;
import com.mygdx.game.Config;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.visibility.VisibilityCalculator;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.decoration.*;
import com.mygdx.game.object.factory.ObjectFactory;
import com.mygdx.game.object.interactive.DungeonEntrance;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.registry.ObjectRegistry;
import com.mygdx.game.registry.TownDataRegistry;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class WorldMapDecorator {

    private final static boolean DEBUG = false;

    private WorldMapDirtSpreadDecorator worldMapDirtSpreadDecorator = new WorldMapDirtSpreadDecorator();

    private RiverCreator riverCreator = new RiverCreator();

    private int deathLimit = 5;
    private int birthLimit = 3;
    private float chanceToStartAlive = 46;

    private final List<Class<? extends WorldObject>> rareDecorations = ImmutableList.<Class<? extends WorldObject>>builder()
            .add(Pond.class)
            .build();

    private final List<Class<? extends WorldObject>> decorations = ImmutableList.<Class<? extends WorldObject>>builder()
            .add(Tree.class)
            .add(TreeV2.class)
            .add(TreeV3.class)
            .add(TreeV4.class)
            .add(TreeV5.class)
            .add(TreeV6.class)
            .add(TreeV7.class)
            .add(TreeV8.class)
            .add(TreeV9.class)
            .add(TreeV10.class)
            .add(Bush.class)
            .add(GiantLeafPlant.class)
            .add(YellowFlower.class)
            .add(BlueFlower.class)
            .add(PineTree.class)
            .add(Rock.class)
            .add(Log.class)
            .build();

    private final List<Class<? extends WorldObject>> grassVariation = ImmutableList.<Class<? extends WorldObject>>builder()
            .add(Grass.class)
            .add(GrassV2.class)
            .build();

    public void decorate(int step, WorldMap worldMap) {

        WorldMap newMap = create(step);

        for (int i = 0; i < TownDataRegistry.INSTANCE.getTownCentreRadius(); i++) {
            for (int j = 0; j < TownDataRegistry.INSTANCE.getTownCentreRadius(); j++) {

            }
        }

        // decoration
        for (int i = 0; i < newMap.getWidth(); i++) {
            for (int j = 0; j < newMap.getHeight(); j++) {
                if (newMap.getTile(i, j).isObstacle()) {

                    boolean rare = new Random().nextInt(100) == 0;

                    if (rare) {
                        int index = new Random().nextInt(rareDecorations.size());
                        if (!ObjectRegistry.INSTANCE.getObjectGrid().containsKey(worldMap) || ObjectRegistry.INSTANCE.getObjectGrid().get(worldMap)[i][j][1] == null)
                            ObjectFactory.create(rareDecorations.get(index), worldMap, ObjectPlacement.FIXED.X(i).Y(j));
                    } else {
                        int index = new Random().nextInt(decorations.size());
                        if (!ObjectRegistry.INSTANCE.getObjectGrid().containsKey(worldMap) || ObjectRegistry.INSTANCE.getObjectGrid().get(worldMap)[i][j][1] == null)
                            ObjectFactory.create(decorations.get(index), worldMap, ObjectPlacement.FIXED.X(i).Y(j));
                    }
                }
            }
        }

        // dirt
        worldMapDirtSpreadDecorator.decorate(2, worldMap);

        // grass
        for (int i = 0; i < newMap.getWidth(); i++) {
            for (int j = 0; j < newMap.getHeight(); j++) {

                int index = new Random().nextInt(grassVariation.size());

                if (worldMap.getTile(i, j).equals(WorldMapTile.GRASS) && ObjectRegistry.INSTANCE.getObjectGrid().get(worldMap)[i][j][1] == null && new Random().nextInt(3) == 0)
                    ObjectFactory.create(grassVariation.get(index), worldMap, ObjectPlacement.FIXED.X(i).Y(j));

            }
        }

        // List<WorldObject> o = ObjectRegistry.INSTANCE.getAll(worldMap).stream().filter(worldObject -> DungeonEntrance.class.isAssignableFrom(worldObject.getClass())).peek(worldObject -> roadCreator.connect(worldMap, worldObject.getCoordinates(), Point.of(5, 5))).collect(Collectors.toList());

        WildlifeDistributor.INSTANCE.populate(worldMap);

        // reveal area of townCenter
        worldMap.getVisibilityCalculator().calculateFor(TownDataRegistry.INSTANCE.getTownCenter(), TownDataRegistry.INSTANCE.getTownCentreRadius(), worldMap, false);

        new DungeonEntrancePlacer().place(worldMap);

        for(int i = 0; i < 3; i++) {
            int x = 0, y = 0, x2, y2;
            do {
                x = new Random().nextInt(worldMap.getWidth());
                y = new Random().nextInt(worldMap.getHeight());

            } while (worldMap.isObstacle(x, y) || worldMap.getTile(x, y).isObstacle());

            do {
                x2 = new Random().nextInt(worldMap.getWidth());
                y2 = new Random().nextInt(worldMap.getHeight());

            } while (worldMap.isObstacle(x2, y2) || worldMap.getTile(x2, y2).isObstacle());

            riverCreator.connect(worldMap, Point.of(x,y), Point.of(x2, y2));
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

        addFrame(cellmap);

        int[] coord = findFirstFloor(cellmap);
        if (coord == null) return null;
        if (DEBUG) {
            int allArea = countTraversableArea(cellmap, WorldMapTile.GRASS);
            fill(coord[0], coord[1], cellmap, WorldMapTile.GRASS, WorldMapTile.EMPTY);
            int traversable = countTraversableArea(cellmap, WorldMapTile.EMPTY);
        }

        System.out.println("Elapsed: " + (System.currentTimeMillis() - start) + " ms");

        return cellmap;
    }

    private int countTraversableArea(WorldMap map, WorldMapTile value) {
        int count = 0;

        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                if (map.getTile(i, j).equals(value)) count++;
            }
        }
        return count;
    }

    private int[] findFirstFloor(WorldMap map) {
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                if (map.getTile(i, j).equals(WorldMapTile.GRASS)) return new int[]{i, j};
            }
        }
        return null;
    }

    private void addFrame(WorldMap map) {
        for (int i = 0; i < Config.WorldMap.WORLD_WIDTH; i++) {
            map.setTile(i, 0, WorldMapTile.WOODEN_WALL);
            map.setTile(i, Config.WorldMap.WORLD_HEIGHT - 1, WorldMapTile.WOODEN_WALL);
        }

        for (int i = 0; i < Config.WorldMap.WORLD_HEIGHT; i++) {
            map.setTile(0, i, WorldMapTile.WOODEN_WALL);
            map.setTile(Config.WorldMap.WORLD_WIDTH - 1, i, WorldMapTile.WOODEN_WALL);
        }
    }

    private WorldMap initialiseMap(WorldMap map) {
        for (int x = 0; x < Config.WorldMap.WORLD_WIDTH; x++) {
            for (int y = 0; y < Config.WorldMap.WORLD_HEIGHT; y++) {
                if (new Random().nextInt(100) < chanceToStartAlive) {
                    map.setTile(x, y, WorldMapTile.WOODEN_WALL);
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
                else if (map.getTile(neighbour_x, neighbour_y) == WorldMapTile.WOODEN_WALL) {
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
                        newMap.setTile(x, y, WorldMapTile.WOODEN_WALL);
                    }
                } //Otherwise, if the cell is dead now, check if it has the right number of neighbours to be 'born'
                else {
                    if (nbs > birthLimit) {
                        newMap.setTile(x, y, WorldMapTile.WOODEN_WALL);
                    } else {
                        newMap.setTile(x, y, WorldMapTile.GRASS);
                    }
                }
            }
        }
        return newMap;
    }

    private void fill(int x, int y, WorldMap oldmap, WorldMapTile originalValue, WorldMapTile value) {
        if (x >= oldmap.getWidth() || y >= oldmap.getHeight() || x < 0 || y < 0 || oldmap.getTile(x, y) != originalValue || oldmap.getTile(x, y) == value) {
            return;
        }

        oldmap.setTile(x, y, value);

        fill(x - 1, y, oldmap, originalValue, value);
        fill(x - 1, y - 1, oldmap, originalValue, value);
        fill(x - 1, y + 1, oldmap, originalValue, value);
        fill(x, y - 1, oldmap, originalValue, value);
        fill(x, y + 1, oldmap, originalValue, value);
        fill(x + 1, y - 1, oldmap, originalValue, value);
        fill(x + 1, y, oldmap, originalValue, value);
        fill(x + 1, y + 1, oldmap, originalValue, value);
    }
}
