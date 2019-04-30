package com.mygdx.game.logic.pathfinding;

import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.logic.Point;

import java.util.ArrayList;
import java.util.List;

public class PathFinder {

    private int width;
    private int height;

    private Node[][] obstacleMap;

    public PathFinder() {
    }

    public PathFinder(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Node[][] getObstacleMap() {
        return obstacleMap;
    }

    public void addObstacle(int x, int y, int value) {
        obstacleMap[x][y].tile = value;
    }

    public List<Node> findAStar(Point start, Point target) {
        Node[][] map = getObstacleMap();
        List<Node> openNodes = new ArrayList<>();
        List<Node> closedNodes = new ArrayList<>();
        List<Node> path = new ArrayList<>();

        Node startNode = new Node(map[start.getX()][start.getY()].tile, start.getX(), start.getY());
        startNode.g = startNode.f = startNode.h = 0;
        startNode.parent = null;

        Node end = new Node(map[target.getX()][target.getY()].tile, target.getX(), target.getY());
        end.g = end.h = end.f = 0;
        end.parent = null;

        openNodes.add(startNode);

        while (!openNodes.isEmpty()) {
            Node current = openNodes.get(0);

            for (int i = 1; i < openNodes.size(); i++) {
                if (openNodes.get(i).f < current.f) {
                    current = openNodes.get(i);
                }
            }

            openNodes.remove(current);
            closedNodes.add(current);

            if ((current.x == target.getX() && current.y == target.getY())) {
                // hurra
                Node c = current;
                while (c != null) {
                    path.add(c);
                    c = c.parent;
                }
                break;
            }

            List<Node> children = new ArrayList<>();
            // add children
            for (int[] i : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}}) {
                int x = current.x - i[0];
                int y = current.y - i[1];

                if (x >= 0 && x < width && y >= 0 && y < height) {

                    if (map[x][y].tile == 1) {
                        continue;
                    }

                    Node child = new Node(map[x][y].tile, x, y);
                    child.parent = current;

                    children.add(child);
                }
            }

            // calculate f,g,h
            for (Node child : children) {
                if (closedNodes.contains(child))
                    continue;
                child.g = current.g + 1;
                child.h = distance(child, end);
                child.f = child.g + child.h;

                if (openNodes.contains(child)) {
                    if (child.g > openNodes.get(openNodes.indexOf(child)).g) continue;
                }
                openNodes.add(child);
            }
        }

        return path;
    }

    public static void main(String[] args) {
        PathFinder pathFinder = new PathFinder(100, 100);
        int[][] obstacleMap = new int[pathFinder.getWidth()][pathFinder.getHeight()];
        pathFinder.init(obstacleMap);

        pathFinder.addObstacle(98, 98, 1);
        int[][] resultMap = new int[pathFinder.getWidth()][pathFinder.getHeight()];

        long start = System.currentTimeMillis();
        List<Node> path = pathFinder.findAStar(new Point(0, 0), new Point(98, 99));
        System.out.println(System.currentTimeMillis() - start);

        int i = 1;
        for (Node node : path) {
            resultMap[node.x][node.y] = i;
            i++;
        }

        pathFinder.print(resultMap);
    }

    private void print(int[][] resultMap) {

        for (int k = 0; k < width; k++) {
            for (int l = 0; l < height; l++) {
                System.out.print(resultMap[k][l]);
            }
            System.out.println("");
        }
    }

    public void init(Map2D map) {
        width = map.getWidth();
        height = map.getHeight();
        obstacleMap = new Node[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                obstacleMap[i][j] = new Node(map.getTile(i, j).isObstacle() ? 1 : 0, i, j);
            }
        }
    }

    public void init(int[][] map) {
        obstacleMap = new Node[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                obstacleMap[i][j] = new Node(map[i][j] != 1 ? 1 : 0, i, j);
            }
        }
    }

    private static int distance(Node n1, Node n2) {
        int a = Math.abs(n2.x - n1.x);
        int b = Math.abs(n2.y - n1.y);
        return a * a + b * b;
    }

    public static class Node {
        private final int x;
        private final int y;
        private Node parent;
        private int tile;

        int f, g, h;

        @Override
        public boolean equals(Object obj) {
            if(!this.getClass().equals(obj.getClass())) {
                return false;
            }
            return ((Node)obj).x ==x && ((Node)obj).y == y;
        }

        public Node(int tile, int x, int y) {
            this.tile = tile;
            this.x = x;
            this.y = y;


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
    }
}