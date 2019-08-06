package com.mygdx.game.object.house;

import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.wall.IncompleteWall;
import com.mygdx.game.object.wall.Wall;
import com.mygdx.game.registry.ObjectRegistry;

import java.util.*;

public class HouseBuiltDetector {

    public static final HouseBuiltDetector INSTANCE = new HouseBuiltDetector();

    public Set<WorldObject> isAHouse(Map2D map, WorldObject worldObject) {

        Deque<WorldObject> open = new ArrayDeque<>();
        Set<WorldObject> closed = new HashSet<>();

        boolean circleClosed = false;

        open.add(worldObject);

        while (!open.isEmpty()) {

            WorldObject current = open.removeFirst();

            closed.add(current);

            WorldObject neightbour = getWorldObject(map, (int) current.getX() - 1, (int) current.getY());

            if (neightbour != null) {

                if (open.contains(neightbour)) {
                    circleClosed = true;
                }

                if (IncompleteWall.class.isAssignableFrom(neightbour.getClass())) {
                    closed.add(neightbour);
                } else if (Wall.class.isAssignableFrom(neightbour.getClass()) && !closed.contains(neightbour)) {
                    open.add(neightbour);
                }
            }

            neightbour = getWorldObject(map, (int) current.getX() + 1, (int) current.getY());

            if (neightbour != null) {

                if (open.contains(neightbour)) {
                    circleClosed = true;
                }

                if (IncompleteWall.class.isAssignableFrom(neightbour.getClass())) {
                    closed.add(neightbour);
                } else if (Wall.class.isAssignableFrom(neightbour.getClass()) && !IncompleteWall.class.isAssignableFrom(neightbour.getClass()) && !closed.contains(neightbour)) {
                    open.add(neightbour);
                }
            }

            neightbour = getWorldObject(map, (int) current.getX(), (int) current.getY() - 1);

            if (neightbour != null) {

                if (open.contains(neightbour)) {
                    circleClosed = true;
                }

                if (IncompleteWall.class.isAssignableFrom(neightbour.getClass())) {
                    closed.add(neightbour);
                } else if (Wall.class.isAssignableFrom(neightbour.getClass()) && !IncompleteWall.class.isAssignableFrom(neightbour.getClass()) && !closed.contains(neightbour)) {
                    open.add(neightbour);
                }
            }

            neightbour = getWorldObject(map, (int) current.getX(), (int) current.getY() + 1);

            if (neightbour != null) {

                if (open.contains(neightbour)) {
                    circleClosed = true;
                }

                if (IncompleteWall.class.isAssignableFrom(neightbour.getClass())) {
                    closed.add(neightbour);
                } else if (Wall.class.isAssignableFrom(neightbour.getClass()) && !IncompleteWall.class.isAssignableFrom(neightbour.getClass()) && !closed.contains(neightbour)) {
                    open.add(neightbour);
                }
            }

        }

        return circleClosed ? closed : Collections.emptySet();
    }

    private WorldObject getWorldObject(Map2D map, int x, int y) {
        if (x < 0 || y < 0 || x >= map.getWidth() || y >= map.getHeight()) {
            return null;
        }
        return ObjectRegistry.INSTANCE.getObjectGrid().get(map)[x][y][1];
    }

}
