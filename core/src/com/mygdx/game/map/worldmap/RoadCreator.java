package com.mygdx.game.map.worldmap;

import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.Obstacle;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.factory.ObjectFactory;
import com.mygdx.game.object.floor.DirtRoad;
import com.mygdx.game.object.interactive.DungeonEntrance;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.registry.ObjectRegistry;

import java.util.*;

public class RoadCreator {

    public void connect(Map2D map, Point p1, Point p2) {
        Set<WorldObject> alreadyBuilt = new HashSet<>();

        Point current = p1;
        boolean returnAfterThis = false;

        int i = 0;
        while ((current.getX() != p2.getX() || current.getY() != p2.getY())) {
            int x, y;

            x = current.getX();
            y = current.getY();

            if (new Random().nextInt(3) != 0) {
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

            if (adjacentRoad(map, current.getX(), current.getY(), alreadyBuilt)) {
                returnAfterThis = true;
            }

            if (i != 0 && worldObject != null && Obstacle.class.isAssignableFrom(worldObject.getClass()) && !DungeonEntrance.class.isAssignableFrom(worldObject.getClass())) {
                ObjectRegistry.INSTANCE.remove(map, worldObject);
            }

            alreadyBuilt.add(ObjectFactory.create(DirtRoad.class, map, ObjectPlacement.FIXED.X(current.getX()).Y(current.getY())));
            map.setTraverseCost(current.getX(), current.getY(), 0.2f);

            if (returnAfterThis) {
                return;
            }

            current = Point.of(x, y);
            i++;
        }

    }

    private boolean adjacentRoad(Map2D map, int x, int y, Set<WorldObject> recentRoad) {
        for (int[] i : new int[][]{{-1, 0},{1, 0}, {0, -1}, {0, 1}}) {

                if ((i[0] == 0 && i[1] == 0) || x + i[0] < 0 || x + i[0] >= map.getWidth() || y + i[1] < 0 || y + i[1] >= map.getHeight())
                    continue;

                WorldObject o = ObjectRegistry.INSTANCE.getObjectGrid().get(map)[x + i[0]][y + i[1]][0];
                if (o != null && !recentRoad.contains(o) && (DirtRoad.class.isAssignableFrom(o.getClass())))
                    return true;

        }
        return false;
    }

}
