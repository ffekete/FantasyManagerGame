package com.mygdx.game.actor.factory;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.creator.map.Map2D;

public enum Placement implements ActorPlacementStrategy {

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
