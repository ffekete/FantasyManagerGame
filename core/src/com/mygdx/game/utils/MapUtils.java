package com.mygdx.game.utils;

import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.TileableObject;
import com.mygdx.game.object.WorldObject;

public class MapUtils {

    public static int bitmask4bit(WorldObject[][] worldObjects, int x, int y) {
        int mask = 0;

        if (y + 1 >= worldObjects[0].length || (worldObjects[x][y + 1] != null && TileableObject.class.isAssignableFrom(worldObjects[x][y + 1].getClass()))) {
            mask += 1;
        }

        if (x + 1 >= worldObjects.length || (worldObjects[x + 1][y] != null && TileableObject.class.isAssignableFrom(worldObjects[x + 1][y].getClass()))) {
            mask += 2;
        }

        if (y - 1 < 0 || (worldObjects[x][y - 1] != null && TileableObject.class.isAssignableFrom(worldObjects[x][y - 1].getClass()))) {
            mask += 4;
        }

        if (x - 1 < 0 || (worldObjects[x - 1][y] != null && TileableObject.class.isAssignableFrom(worldObjects[x - 1][y].getClass()))) {
            mask += 8;
        }

        return mask;
    }

    public static int bitmask4bit(Map2D map, int x, int y) {
        int mask = 0;

        if (y + 1 >= map.getHeight() || map.getTile(x, y + 1).isObstacle()) {
            mask += 1;
        }

        if (x + 1 >= map.getWidth() || map.getTile(x + 1, y).isObstacle()) {
            mask += 2;
        }

        if (y - 1 < 0 || map.getTile(x, y - 1).isObstacle()) {
            mask += 4;
        }

        if (x - 1 < 0 || map.getTile(x - 1, y).isObstacle()) {
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
