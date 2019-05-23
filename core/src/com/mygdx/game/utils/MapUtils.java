package com.mygdx.game.utils;

import com.mygdx.game.creator.map.Map2D;

public class MapUtils {

    public static int bitmask8bit(Map2D map, int x, int y) {
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

}
