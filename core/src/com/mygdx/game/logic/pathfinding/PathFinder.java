package com.mygdx.game.logic.pathfinding;

import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Config;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.logic.Point;

import java.util.ArrayList;
import java.util.List;

public class PathFinder {

    private final Pool<Node> nodePool = new Pool<Node>() {
        @Override
        protected Node newObject() {
            return new Node(0,0,0);
        }
    };

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
        obstacleMap[x][y].tile = value;
    }

    public List<Node> findAStar(Point start, Point target) {
        Node[][] map = getObstacleMap();
        List<Node> openNodes = new ArrayList<>();
        List<Node> closedNodes = new ArrayList<>();
        List<Node> path = new ArrayList<>();
        List<Node> allNodes = new ArrayList<>();

        Node startNode = nodePool.obtain();

        startNode.tile = map[start.getX()][start.getY()].tile;
        startNode.x = start.getX();
        startNode.y = start.getY();

        startNode.g = startNode.f = startNode.h = 0;
        startNode.parent = null;

        Node end = nodePool.obtain();
        end.tile = map[target.getX()][target.getY()].tile;
        end.x = target.getX();
        end.y = target.getY();

        end.g = end.h = end.f = 0;
        end.parent = null;

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

            if ((current.x == target.getX() && current.y == target.getY()) ||
                    map[target.getX()][target.getY()].tile == 1 && distance(current, new Node(0, target.getX(), target.getY())) < 2
                    ) {
                // hurra
                Node c = current;
                while (c != null) {
                    Node c1 = new Node(c.tile, c.x, c.y);
                    path.add(c1);
                    c = c.parent;
                }
                break;
            }

            List<Node> children = new ArrayList<>();
            // add children
            for (int[] i : availableNodesForCheck) {
                int x = current.x - i[0];
                int y = current.y - i[1];

                if (x >= 0 && x < width && y >= 0 && y < height) {

                    if (map[x][y].tile == 1) {
                        continue;
                    }

                    Node child = nodePool.obtain();
                    allNodes.add(child);
                    child.tile = map[x][y].tile;
                    child.x = x;
                    child.y = y;

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

        for(Node n : allNodes) {
            nodePool.free(n);
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

                obstacleMap[i][j] = new Node(map.getTile(i, j).isObstacle() || map.isObstacle(i,j) ? 1 : 0, i, j);
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

    public static class Node implements Pool.Poolable {
        private int x;
        private int y;
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

        @Override
        public int hashCode() {
            int hash = 31 * x;
            return 31 * hash + y;
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

        @Override
        public void reset() {
            tile = 0;
        }
    }
}