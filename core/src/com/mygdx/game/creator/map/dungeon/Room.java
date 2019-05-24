package com.mygdx.game.creator.map.dungeon;

public class Room{

    private int x;
    private int y;
    private int width;
    private int height;
    private boolean empty =  true;
    private boolean corridor = false;

    public Room(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMaxX() {
        return x + width;
    }

    public int getMaxY() {
        return y + height;
    }

    public void setEmpty(boolean value) {
        this.empty = value;
    }

    public boolean isEmpty() {
        return empty;
    }

    @Override
    public String toString() {
        return "" + x + " " + y + " " + width + " " + height;
    }
}
