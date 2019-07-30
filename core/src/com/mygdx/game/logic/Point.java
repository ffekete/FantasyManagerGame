package com.mygdx.game.logic;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

public class Point implements Pool.Poolable {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Point of(int x, int y) {
        return new Point(x, y);
    }

    public static Point of(float x, float y) {
        return new Point((int) x, (int) y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void update(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void reset() {
        x = 0;
        y = 0;
    }

    @Override
    public int hashCode() {
        int h = 31 + x;
        return h * 31 + y;
    }

    @Override
    public boolean equals(Object obj) {
        if (Point.class.equals(obj.getClass())) {
            return ((Point) obj).getX() == x && ((Point) obj).getY() == y;
        }
        return false;
    }

    public static Point of(Vector3 realWorldCoord) {
        return Point.of(realWorldCoord.x, realWorldCoord.y);
    }
}