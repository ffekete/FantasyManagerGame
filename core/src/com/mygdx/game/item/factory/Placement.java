package com.mygdx.game.item.factory;

import com.mygdx.game.item.Item;
import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Map2D;

import java.util.Random;

public enum Placement implements ItemPlacementStrategy {

    RANDOM() {
        @Override
        public void place(Item item, Map2D map) {
            int x = 0,y = 0;
            do {
                x = new Random().nextInt(map.getWidth());
                y = new Random().nextInt(map.getHeight());

            } while(map.getTile(x,y).isObstacle());

            item.setCoordinates(new Point(x,y));
        }

        @Override
        public Placement X(int x) {
            return null;
        }

        @Override
        public Placement Y(int y) {
            return null;
        }
    },

    FIXED(){

        private int x = 0;
        private int y = 0;

        @Override
        public void place(Item item, Map2D map) {
            item.setCoordinates(new Point(x, y));
        }

        public Placement X(int xValue) {
            this.x = xValue;
            return this;
        }

        public Placement Y(int yValue) {
            this.y = yValue;
            return this;
        }
    }
}
