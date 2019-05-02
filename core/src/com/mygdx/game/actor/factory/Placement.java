package com.mygdx.game.actor.factory;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.creator.map.Map2D;

import java.util.Random;

public enum Placement implements ActorPlacementStrategy {

    RANDOM() {
        @Override
        public void place(Actor actor, Map2D map) {
            int x = 0,y = 0;
            do {
                x = new Random().nextInt(map.getWidth());
                y = new Random().nextInt(map.getHeight());

            } while(map.getTile(x,y).isObstacle());

            actor.setCoordinates(x,y);
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
        public void place(Actor actor, Map2D map) {
            actor.setCoordinates(x, y);
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
