package com.mygdx.game.object.factory;

import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.floor.WoodenFloor;
import com.mygdx.game.object.furniture.WoodenBed;
import com.mygdx.game.object.interactive.PracticeFigure;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.object.wall.WoodenWall;
import com.mygdx.game.object.wall.WoodenWallDoor;
import com.mygdx.game.registry.ObjectRegistry;

public class HouseFactory {

    public static final HouseFactory INSTANCE = new HouseFactory();

    public void create(int x, int y, int length, Map2D map) {

        // reset area
        for (int i = x; i <= x + length; i++)
            for (int j = y; j <= y + length; j++) {
                ObjectRegistry.INSTANCE.getObjectGrid().computeIfAbsent(map, v -> new WorldObject[map.getWidth()][map.getHeight()][2]);
                WorldObject object = ObjectRegistry.INSTANCE.getObjectGrid().get(map)[i][j][0];
                if (object != null)
                    ObjectFactory.remove(map, object);
                object = ObjectRegistry.INSTANCE.getObjectGrid().get(map)[i][j][1];
                if (object != null)
                    ObjectFactory.remove(map, object);

                ObjectFactory.create(WoodenFloor.class, map, ObjectPlacement.FIXED.X(i).Y(j));
            }
        // build wall
        for (int i = x; i <= x + length; i++) {
            ObjectFactory.create(WoodenWall.class, map, ObjectPlacement.FIXED.X(i).Y(y));
            ObjectFactory.create(WoodenWall.class, map, ObjectPlacement.FIXED.X(i).Y(y + length));
        }

        for (int i = y; i <= y + length; i++) {
            ObjectFactory.create(WoodenWall.class, map, ObjectPlacement.FIXED.X(x).Y(i));
            ObjectFactory.create(WoodenWall.class, map, ObjectPlacement.FIXED.X(x + length).Y(i));
        }

        ObjectFactory.remove(map, ObjectRegistry.INSTANCE.getObjectGrid().get(map)[x + length / 2][y + length][1]);

        ObjectFactory.create(WoodenWallDoor.class, map, ObjectPlacement.FIXED.X(x + length / 2).Y(y + length));

        ObjectFactory.create(WoodenBed.class, map, ObjectPlacement.FIXED.X(x + 1).Y(y + 1));

        ObjectFactory.create(PracticeFigure.class, map, ObjectPlacement.FIXED.X(x + 2).Y(y + 1));


    }

}
