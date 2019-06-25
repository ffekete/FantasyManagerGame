package com.mygdx.game.map.dungeon.decorator;

import com.mygdx.game.Config;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.factory.ObjectFactory;
import com.mygdx.game.object.placement.ObjectPlacement;

import java.util.Random;

public enum Decoration implements DecorationItem {

    SpiderWeb {
        @Override
        public boolean place(Map2D map, int mask, int x, int y) {
            if(mask == 3 && !map.getTile(x,y).isObstacle() && new Random().nextInt(Config.Dungeon.WORLD_OBJECT_SPAWN_RATE) == 0) {
                ObjectFactory.create(com.mygdx.game.object.decoration.SpiderWeb.class, map, ObjectPlacement.FIXED.X(x).Y(y));
                return true;
            }
            return false;
        }
    }

}
