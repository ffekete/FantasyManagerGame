package com.mygdx.game.object.placement;

import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.Obstacle;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.floor.Floor;
import com.mygdx.game.registry.ObjectRegistry;

import java.util.Random;

public enum ObjectPlacement implements ObjectPlacementStrategy {

    RANDOM() {
        @Override
        public void place(WorldObject object, Map2D map) {
            int x = 0, y = 0;
            do {
                x = new Random().nextInt(map.getWidth());
                y = new Random().nextInt(map.getHeight());

            } while (map.isObstacle(x, y) || map.getTile(x, y).isObstacle());

            if (Obstacle.class.isAssignableFrom(object.getClass()))
                map.setObstacle(x, y, true);
            else {
                int index = Floor.class.isAssignableFrom(object.getClass()) ? 1 : 0;
                ObjectRegistry.INSTANCE.getObjectGrid().computeIfAbsent(map, v -> new WorldObject[map.getWidth()][map.getHeight()][2]);
                WorldObject worldObject = ObjectRegistry.INSTANCE.getObjectGrid().get(map)[x][y][index];
                if (worldObject != null)
                    map.setObstacle(x, y, Obstacle.class.isAssignableFrom(worldObject.getClass()));
            }

            object.setCoordinates(new Point(x, y));
        }

        @Override
        public ObjectPlacement X(int x) {
            return this;
        }

        @Override
        public ObjectPlacement Y(int y) {
            return this;
        }
    },

    FIXED() {

        private int x = 0;
        private int y = 0;

        @Override
        public void place(WorldObject object, Map2D map) {
            object.setCoordinates(new Point(x, y));
            if (Obstacle.class.isAssignableFrom(object.getClass())) {
                map.setObstacle(x, y, true);
            } else {
                int index = Floor.class.isAssignableFrom(object.getClass()) ? 1 : 0;
                WorldObject[][][] objects = ObjectRegistry.INSTANCE.getObjectGrid().get(map);
                if(objects != null) {
                    WorldObject worldObject = objects[x][y][index];
                    if (worldObject != null)
                        map.setObstacle(x, y, Obstacle.class.isAssignableFrom(worldObject.getClass()));
                }
            }
        }

        public ObjectPlacement X(int xValue) {
            this.x = xValue;
            return this;
        }

        public ObjectPlacement Y(int yValue) {
            this.y = yValue;
            return this;
        }
    }
}
