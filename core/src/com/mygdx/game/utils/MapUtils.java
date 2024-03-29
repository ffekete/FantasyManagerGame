package com.mygdx.game.utils;

import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.worldmap.WorldMap;
import com.mygdx.game.map.worldmap.WorldMapTile;
import com.mygdx.game.object.TileableObject;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.floor.TileableFloorObject;

public class MapUtils {

    public static int bitmask4bit(WorldObject[][][] worldObjects, int x, int y, Class clazz) {
        int mask = 0;
        int index = TileableFloorObject.class.isAssignableFrom(clazz) ? 0 : 1;

        if (y + 1 < worldObjects[0].length && (worldObjects[x][y + 1][index] != null && (isConnectible(worldObjects[x][y + 1][index], clazz)))) {
            mask += 1;
        }

        if (x + 1 < worldObjects.length && (worldObjects[x + 1][y][index] != null && isConnectible(worldObjects[x + 1][y][index], clazz))) {
            mask += 2;
        }

        if (y - 1 >= 0 && (worldObjects[x][y - 1][index] != null && isConnectible(worldObjects[x][y - 1][index], clazz))) {
            mask += 4;
        }

        if (x - 1 >= 0 && (worldObjects[x - 1][y][index] != null && isConnectible(worldObjects[x - 1][y][index], clazz))) {
            mask += 8;
        }

        return mask;
    }

    private static boolean isConnectible(WorldObject worldObject, Class clazz) {

        if (TileableObject.class.isAssignableFrom(worldObject.getClass())) {
            if(((TileableObject)worldObject).getConnectableTypes().stream().anyMatch(aClass -> aClass.isAssignableFrom(clazz)))
                return true;
        }

        return false;
    }




    public static int bitmask4bitForTile(WorldMap map, int x, int y, WorldMapTile tile, int skip) {
        int mask = 0;

        if (y + skip < map.getHeight() && map.getTile(x, y + skip).equals(tile)) {
            mask += 1;
        }

        if (x + skip < map.getWidth() && map.getTile(x + skip, y).equals(tile)) {
            mask += 2;
        }

        if (y - skip >= 0 && map.getTile(x, y - skip).equals(tile)) {
            mask += 4;
        }

        if (x - skip >= 0 && map.getTile(x - skip, y).equals(tile)) {
            mask += 8;
        }

        return mask;
    }

    public static int bitmask4bit(Map2D map, int x, int y) {
        int mask = 0;

        if (y + 1 < map.getHeight() && map.getTile(x, y + 1).isObstacle()) {
            mask += 1;
        }

        if (x + 1 < map.getWidth() && map.getTile(x + 1, y).isObstacle()) {
            mask += 2;
        }

        if (y - 1 >= 0 && map.getTile(x, y - 1).isObstacle()) {
            mask += 4;
        }

        if (x - 1 >= 0 && map.getTile(x - 1, y).isObstacle()) {
            mask += 8;
        }

        return mask;
    }

    public static int bitmask8bit(Map2D map, int x, int y) {
        int mask = 0;

        if (y + 1 >= map.getHeight() || x - 1 < 0 || map.getTile(x - 1, y + 1).isObstacle()) {
            mask += 1;
        }

        if (y + 1 >= map.getHeight() || map.getTile(x, y + 1).isObstacle()) {
            mask += 2;
        }

        if ((x + 1 >= map.getWidth() || y + 1 >= map.getHeight()) || map.getTile(x + 1, y + 1).isObstacle()) {
            mask += 4;
        }

        if (x + 1 >= map.getWidth() || map.getTile(x + 1, y).isObstacle()) {
            mask += 8;
        }

        if (x + 1 >= map.getWidth() || y - 1 < 0 || map.getTile(x + 1, y - 1).isObstacle()) {
            mask += 16;
        }

        if (y - 1 < 0 || map.getTile(x, y - 1).isObstacle()) {
            mask += 32;
        }

        if (x - 1 < 0 || y - 1 < 0 || map.getTile(x - 1, y - 1).isObstacle()) {
            mask += 64;
        }

        if (x - 1 < 0 || map.getTile(x - 1, y).isObstacle()) {
            mask += 128;
        }

        return mask;
    }

}
