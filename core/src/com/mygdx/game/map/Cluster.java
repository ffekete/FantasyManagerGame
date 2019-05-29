package com.mygdx.game.map;

import com.mygdx.game.Config;

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
