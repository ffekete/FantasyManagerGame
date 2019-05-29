package com.mygdx.game.creator.map.dungeon.cave;

import com.mygdx.game.Config;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.creator.map.dungeon.Decorator;
import com.mygdx.game.object.decoration.SpiderWeb;
import com.mygdx.game.object.factory.WorldObjectFactory;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.utils.MapUtils;

import java.util.Random;

public class CaveDungeonDecorator implements Decorator {

    public static final CaveDungeonDecorator INSTANCE = new CaveDungeonDecorator();

    @Override
    public void decorate(Map2D map) {
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {

                int mask = MapUtils.bitmask4bit(map, i,j);

                if(mask == 3 && !map.getTile(i,j).isObstacle() && new Random().nextInt(Config.Dungeon.WORLD_OBJECT_SPAWN_RATE) == 0) {
                    WorldObjectFactory.create(SpiderWeb.class, map, ObjectPlacement.FIXED.X(i).Y(j));
                }
            }
        }
    }

    private CaveDungeonDecorator() {
    }
}
