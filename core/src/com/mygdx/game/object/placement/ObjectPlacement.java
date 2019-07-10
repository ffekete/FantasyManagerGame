package com.mygdx.game.object.placement;

import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.logic.Point;

import java.util.Random;

public enum ObjectPlacement implements ObjectPlacementStrategy {

    RANDOM() {
        @Override
        public void place(WorldObject object, Map2D map) {
            int x = 0,y = 0;
            do {
                x = new Random().nextInt(map.getWidth());
                y = new Random().nextInt(map.getHeight());

            } while(map.isObstacle(x,y) || map.getTile(x,y).isObstacle());

            map.setObstacle(x,y,true);

            object.setCoordinates(new Point(x,y));
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

    FIXED(){

        private int x = 0;
        private int y = 0;

        @Override
        public void place(WorldObject object, Map2D map) {
            object.setCoordinates(new Point(x, y));
            map.setObstacle(x,y,true);
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
