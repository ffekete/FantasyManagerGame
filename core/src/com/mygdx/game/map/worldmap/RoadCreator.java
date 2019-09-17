package com.mygdx.game.map.worldmap;

import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.pathfinding.Node;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.Obstacle;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.factory.ObjectFactory;
import com.mygdx.game.object.floor.DirtRoad;
import com.mygdx.game.object.interactive.DungeonEntrance;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.ObjectRegistry;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RoadCreator {

    public void connect(Map2D map, Point p1, Point p2) {
        PathFinder pathFinder = MapRegistry.INSTANCE.getPathFinderFor(map);

        Set<WorldObject> alreadyBuilt = new HashSet<>();

        boolean returnAfterThis = false;

        List<Node> path = pathFinder.findAStar(p1, p2);

        int i = 0;
        for(Node node : path) {
            if(ObjectRegistry.INSTANCE.getObjectGrid().get(map)[node.getX()][node.getY()][1] != null && !DungeonEntrance.class.isAssignableFrom(ObjectRegistry.INSTANCE.getObjectGrid().get(map)[node.getX()][node.getY()][1].getClass())) {
                ObjectFactory.remove(map, node.getX(), node.getY());
            }

            WorldObject worldObject = ObjectRegistry.INSTANCE.getObjectGrid().get(map)[node.getX()][node.getY()][1];

            if (adjacentRoad(map, node.getX(), node.getY(), alreadyBuilt)) {
                returnAfterThis = true;
            }

            if (i != 0 && worldObject != null && Obstacle.class.isAssignableFrom(worldObject.getClass()) && !DungeonEntrance.class.isAssignableFrom(worldObject.getClass())) {
                ObjectRegistry.INSTANCE.remove(map, worldObject);
            }
            i++;

            alreadyBuilt.add(ObjectFactory.create(DirtRoad.class, map, ObjectPlacement.FIXED.X(node.getX()).Y(node.getY())));
            map.setTraverseCost(node.getX(), node.getY(), 0.2f);

            if (returnAfterThis) {
                return;
            }
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
