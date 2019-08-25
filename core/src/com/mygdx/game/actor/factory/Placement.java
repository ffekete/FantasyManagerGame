package com.mygdx.game.actor.factory;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.logic.Point;
import com.mygdx.game.registry.ActorRegistry;

import java.util.Random;

public enum Placement implements ActorPlacementStrategy {

    RANDOM() {
        @Override
        public void place(Actor actor, Map2D map) {
            int x = 0, y = 0;
            do {
                x = new Random().nextInt(map.getWidth());
                y = new Random().nextInt(map.getHeight());

            } while (map.isObstacle(x, y) || map.getTile(x, y).isObstacle());

            ActorRegistry.INSTANCE.remove(actor.getCurrentMap(), actor);
            actor.setCoordinates(new Point(x, y));
            ActorRegistry.INSTANCE.add(actor.getCurrentMap(), actor);
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

    FIXED() {

        private int x = 0;
        private int y = 0;

        @Override
        public void place(Actor actor, Map2D map) {
            ActorRegistry.INSTANCE.remove(actor.getCurrentMap(), actor);
            actor.setCoordinates(new Point(x, y));
            ActorRegistry.INSTANCE.add(actor.getCurrentMap(), actor);
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
