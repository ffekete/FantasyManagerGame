package com.mygdx.game.logic;

public class Point {
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
}