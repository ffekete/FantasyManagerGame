package com.mygdx.game.logic;

import com.badlogic.gdx.utils.Pool;

public class Point implements Pool.Poolable {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Point of(int x, int y) {
        return new Point(x,y);
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
}