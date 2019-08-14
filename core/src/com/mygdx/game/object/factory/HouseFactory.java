package com.mygdx.game.object.factory;

import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.furniture.WoodenBed;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.object.wall.WoodenWall;
import com.mygdx.game.object.wall.WoodenWallDoor;
import com.mygdx.game.registry.ObjectRegistry;

public class HouseFactory {

    public static final HouseFactory INSTANCE = new HouseFactory();

    public void create(int x, int y, int length, Map2D map) {
        for(int i = x; i <= x + length; i++) {
            ObjectFactory.create(WoodenWall.class, map, ObjectPlacement.FIXED.X(i).Y(y));
            ObjectFactory.create(WoodenWall.class, map, ObjectPlacement.FIXED.X(i).Y(y+length));
        }

        for(int i = y; i <= y + length; i++) {
            ObjectFactory.create(WoodenWall.class, map, ObjectPlacement.FIXED.X(x).Y(i));
            ObjectFactory.create(WoodenWall.class, map, ObjectPlacement.FIXED.X(x + length).Y(i));
        }

        ObjectFactory.remove(map, ObjectRegistry.INSTANCE.getObjectGrid().get(map)[x + length / 2][y + length][1]);

        ObjectFactory.create(WoodenWallDoor.class, map, ObjectPlacement.FIXED.X(x + length / 2).Y(y + length));

        ObjectFactory.create(WoodenBed.class, map, ObjectPlacement.FIXED.X(x + 1).Y(y + 1));
    }

}
