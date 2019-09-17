package com.mygdx.game.logic.pathfinding;

import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Config;
import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Map2D;
import com.sun.deploy.util.ReflectionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PathFinder {

    public static final float DEFAULT_EFFORT = 100;

    // 8 ways pathfinding
    //private static final int[][] availableNodesForCheck = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

    // 4 ways pathfinding
    private static final int[][] availableNodesForCheck = Config.Engine.ENABLE_8_WAYS_PATHFINDING ?
            new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}} :
            new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

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
        obstacleMap[x][y].setTile(value);
    }

    public List<Node> findAStar(Point start, Point target) {
        Node[][] map = getObstacleMap();
        List<Node> openNodes = new ArrayList<>();
        List<Node> closedNodes = new ArrayList<>();
        List<Node> path = new ArrayList<>();
        List<Node> allNodes = new ArrayList<>();

        Node startNode = new Node(map[start.getX()][start.getY()].getTile(), start.getX(), start.getY(), DEFAULT_EFFORT);
        try {
            System.out.println("&&&&&&&");

            System.out.println(startNode);

            System.out.println(start.getX());
            System.out.println(start.getY());
            System.out.println(map[start.getX()][start.getY()].getTile());
        } catch (NullPointerException e) {
            // wtf?
            System.out.println("WTF?");
        }
        startNode.setX(start.getX());
        startNode.setY(start.getY());
        startNode.effort = map[startNode.getX()][startNode.getY()].effort;

        startNode.g = startNode.f = startNode.h = 0f;
        startNode.setParent(null);

        Node end = new Node(0, 0, 0, DEFAULT_EFFORT);
        end.setTile(map[target.getX()][target.getY()].getTile());
        end.setX(target.getX());
        end.setY(target.getY());

        end.g = end.h = end.f = 0f;

        end.effort = map[end.getX()][end.getY()].effort;
        end.setParent(null);

        openNodes.add(startNode);

        allNodes.add(startNode);
        allNodes.add(end);


        while (!openNodes.isEmpty()) {
            Node current = openNodes.get(0);

            for (int i = 1; i < openNodes.size(); i++) {
                if (openNodes.get(i).f < current.f) {
                    current = openNodes.get(i);
                }
            }

            openNodes.remove(current);
            closedNodes.add(current);

            if ((current.getX() == target.getX() && current.getY() == target.getY()) ||
                    map[target.getX()][target.getY()].getTile() == 1 && distance(current, new Node(0, target.getX(), target.getY(), 0.f)) < 2
                    ) {
                // hurra
                Node c = current;
                while (c != null) {
                    Node c1 = new Node(c.getTile(), c.getX(), c.getY(), c.effort);
                    path.add(c1);
                    c = c.getParent();
                }
                return path;
            }

            List<Node> children = new ArrayList<>();
            // add children
            for (int[] i : availableNodesForCheck) {
                int x = current.getX() - i[0];
                int y = current.getY() - i[1];

                if (x >= 0 && x < width && y >= 0 && y < height) {

                    if (map[x][y].getTile() == 1) {
                        continue;
                    }

                    Node child = new Node(0, 0, 0, DEFAULT_EFFORT);
                    allNodes.add(child);
                    child.setTile(map[x][y].getTile());
                    child.setX(x);
                    child.setY(y);
                    child.effort = map[x][y].effort;

                    child.setParent(current);

                    children.add(child);
                }
            }

            // calculate f,g,h
            for (Node child : children) {
                if (closedNodes.contains(child))
                    continue;
                child.g = current.g + child.effort;
                child.h = (float) distance(child, end);
                child.f = child.g + child.h;

                if (openNodes.contains(child)) {
                    if (child.g > openNodes.get(openNodes.indexOf(child)).g) {
                        continue;

                    }
                } else
                    openNodes.add(child);
            }
        }

        return Collections.emptyList();
    }

    public static void main(String[] args) {
        PathFinder pathFinder = new PathFinder(100, 100);
        int[][] obstacleMap = new int[pathFinder.getWidth()][pathFinder.getHeight()];
        pathFinder.init(obstacleMap);

        pathFinder.getObstacleMap()[0][0].effort = 0.2f;
        pathFinder.getObstacleMap()[0][1].effort = 0.2f;
        pathFinder.getObstacleMap()[0][2].effort = 0.2f;
        pathFinder.getObstacleMap()[0][3].effort = 0.2f;
        pathFinder.getObstacleMap()[0][4].effort = 0.2f;
        pathFinder.getObstacleMap()[0][5].effort = 0.2f;
        pathFinder.getObstacleMap()[0][6].effort = 0.2f;
        pathFinder.getObstacleMap()[0][7].effort = 0.2f;
        pathFinder.getObstacleMap()[0][8].effort = 0.2f;
        pathFinder.getObstacleMap()[0][9].effort = 0.2f;
        pathFinder.getObstacleMap()[0][10].effort = 0.2f;
        pathFinder.getObstacleMap()[0][11].effort = 0.2f;
        pathFinder.getObstacleMap()[0][12].effort = 0.2f;

        pathFinder.addObstacle(98, 98, 1);
        int[][] resultMap = new int[pathFinder.getWidth()][pathFinder.getHeight()];

        long start = System.currentTimeMillis();
        List<Node> path = pathFinder.findAStar(new Point(0, 0), new Point(98, 99));
        System.out.println(System.currentTimeMillis() - start);

        int i = 1;
        for (Node node : path) {
            resultMap[node.getX()][node.getY()] = 1;
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

                obstacleMap[i][j] = new Node(map.getTile(i, j).isObstacle() || map.isObstacle(i, j) || map.isNoViewBlockingObstacle(i, j) ? 1 : 0, i, j, map.getTraverseCost(i, j));
            }
        }
    }

    public void init(int[][] map) {
        obstacleMap = new Node[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                obstacleMap[i][j] = new Node(map[i][j], i, j, DEFAULT_EFFORT);
            }
        }
    }

    private static int distance(Node n1, Node n2) {
        int a = Math.abs(n2.getX() - n1.getX());
        int b = Math.abs(n2.getY() - n1.getY());
        return a * a + b * b;
    }
}