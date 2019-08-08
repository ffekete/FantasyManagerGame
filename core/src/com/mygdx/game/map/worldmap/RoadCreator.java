package com.mygdx.game.map.worldmap;

import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.Obstacle;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.factory.ObjectFactory;
import com.mygdx.game.object.floor.Road;
import com.mygdx.game.object.interactive.DungeonEntrance;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.registry.ObjectRegistry;

import java.util.*;

public class RoadCreator {

    public void connect(Map2D map, Point p1, Point p2) {
        Deque<Point> open = new ArrayDeque<>();
        List<Point> closed = new ArrayList<>();

        Point current = p1;

        int i = 0;
        while ((current.getX() != p2.getX() || current.getY() != p2.getY())) {
            int x, y;

            x = current.getX();
            y = current.getY();

            if (new Random().nextInt(2) == 0) {
                if (current.getX() < p2.getX()) {
                    x = current.getX() + 1;
                } else if (current.getX() > p2.getX()) {
                    x = current.getX() - 1;
                }
            } else {

                if (current.getY() < p2.getY()) {
                    y = current.getY() + 1;
                } else if (current.getY() > p2.getY()) {
                    y = current.getY() - 1;
                }
            }

            WorldObject worldObject = ObjectRegistry.INSTANCE.getObjectGrid().get(map)[current.getX()][current.getY()][1];

            if (i != 0 && worldObject != null && Obstacle.class.isAssignableFrom(worldObject.getClass()) && !DungeonEntrance.class.isAssignableFrom(worldObject.getClass())) {
                ObjectRegistry.INSTANCE.remove(map, worldObject);
            }

            ObjectFactory.create(Road.class, map, ObjectPlacement.FIXED.X(current.getX()).Y(current.getY()));
            map.setTraverseCost(current.getX(), current.getY(), 1);

            current = Point.of(x, y);
            i++;
        }

    }

}
