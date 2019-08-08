package com.mygdx.game.object.factory;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.animation.object.WorldObjectAnimation;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.AnimatedObject;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.floor.Floor;
import com.mygdx.game.object.furniture.Furniture;
import com.mygdx.game.object.house.House;
import com.mygdx.game.object.house.HouseBuiltDetector;
import com.mygdx.game.object.light.LightSource;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.object.wall.Wall;
import com.mygdx.game.registry.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ObjectFactory {

    private static final HouseBuilder houseBuilder = HouseBuilder.INSTANCE;

    public static <T extends WorldObject> T create(Class<T> clazz, Map2D map2D, ObjectPlacement objectPlacement) {

        T object = null;
        Point point = Point.of(0, 0);
        try {
            object = clazz.getConstructor(Point.class).newInstance(point);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (object != null) {

            objectPlacement.place(object, map2D);

            ObjectRegistry.INSTANCE.add(map2D, Cluster.of(object.getX(), object.getY()), object);

            if (Wall.class.isAssignableFrom(object.getClass())) {
                HouseBuilder.buildHouse(clazz, map2D, object);
            }

            if(Furniture.class.isAssignableFrom(object.getClass())) {
                FurnitureToHouseAssigner.INSTANCE.assign((Furniture) object);
            }
            if(Floor.class.isAssignableFrom(object.getClass())) {
                FloorToHouseAssigner.INSTANCE.assign((Floor) object);
            }

            if (AnimatedObject.class.isAssignableFrom(clazz))
                AnimationRegistry.INSTANCE.add((AnimatedObject) object, new WorldObjectAnimation((AnimatedObject) object));

            if (LightSource.class.isAssignableFrom(clazz))
                LightSourceRegistry.INSTANCE.add(map2D, (LightSource) object);

            // todo set objects to obstacle
            // map2D.getTile((float)object.getX(), (float)object.getY()).
        }
        return object;
    }
}
