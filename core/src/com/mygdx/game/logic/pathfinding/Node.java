package com.mygdx.game.logic.pathfinding;

public class Node {
    private int x;
    private int y;
    private Node parent;
    private int tile;
    float effort;
    float f, g, h;

    @Override
    public boolean equals(Object obj) {
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        return ((Node) obj).x == x && ((Node) obj).y == y && effort == ((Node) obj).effort;
    }

    @Override
    public int hashCode() {
        int hash = 31 * x;
        hash = 31 * hash + Float.hashCode(effort);
        return 31 * hash + y;
    }

    public Node(int tile, int x, int y, float effort) {
        this.tile = tile;
        this.x = x;
        this.y = y;
        this.effort = effort;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTile() {
        return tile;
    }

    public Node getParent() {
        return parent;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setTile(int tile) {
        this.tile = tile;
    }

    public void setEffort(float effort) {
        this.effort = effort;
    }

    public void setF(float f) {
        this.f = f;
    }

    public void setG(float g) {
        this.g = g;
    }

    public void setH(float h) {
        this.h = h;
    }

    public float getEffort() {
        return effort;
    }

    public float getF() {
        return f;
    }

    public float getG() {
        return g;
    }

    public float getH() {
        return h;
    }
}
