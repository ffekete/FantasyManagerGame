package com.mygdx.game.creator.map;

public class Cluster {

    private final int x;
    private final int y;

    public Cluster(int x, int y) {
        this.x = x;
        this.y = y;
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
