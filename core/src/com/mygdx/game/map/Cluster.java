package com.mygdx.game.map;

import com.mygdx.game.Config;

import java.util.ArrayList;
import java.util.List;

public class Cluster {

    private final int x;
    private final int y;

    public Cluster(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Cluster of(float x, float y) {
        int nx = (int)x / Config.WorldMap.CLUSTER_DIVIDER;
        int ny = (int)y / Config.WorldMap.CLUSTER_DIVIDER;

        return new Cluster(nx, ny);
    }

    public static List<Cluster> ofSurrounding(Map2D map, float x, float y, int radius) {

        List<Cluster> clusters = new ArrayList<>();

        for(int i = 0 - radius / 2; i <= radius / 2; i++) {
            for (int j = 0 - radius / 2; j <= radius / 2; j++) {

                int nx = (int)x / Config.WorldMap.CLUSTER_DIVIDER + i;
                int ny = (int)y / Config.WorldMap.CLUSTER_DIVIDER + j;

                if(nx >= 0 && nx < map.getWidth() && ny >= 0 && ny < map.getHeight()) {
                    clusters.add(new Cluster(nx, ny));
                }
            }
        }
        return clusters;
    }

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().isAssignableFrom(this.getClass())) {
            return false;
        }

        return ((Cluster)obj).x == x && ((Cluster)obj).y == y;
    }

    @Override
    public int hashCode() {
        int hash =  31 * x;
        return hash * 31 + y;
    }
}
