package com.mygdx.game.object.factory;

import com.mygdx.game.animation.object.WorldObjectAnimation;
import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.AnimatedObject;
import com.mygdx.game.object.StorageArea;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.floor.Floor;
import com.mygdx.game.object.floor.Road;
import com.mygdx.game.object.furniture.Furniture;
import com.mygdx.game.object.light.LightSource;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.object.wall.Wall;
import com.mygdx.game.registry.AnimationRegistry;
import com.mygdx.game.registry.LightSourceRegistry;
import com.mygdx.game.registry.ObjectRegistry;
import com.mygdx.game.registry.StorageRegistry;

import java.lang.reflect.InvocationTargetException;

public class ObjectFactory {

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

            if (Road.class.isAssignableFrom(clazz)) {
                map2D.setTraverseCost((int) object.getX(), (int) object.getY(), 0.2f);
            }

            if (StorageArea.class.isAssignableFrom(object.getClass())) {
                StorageRegistry.INSTANCE.add((StorageArea) object);
            }

            if (Wall.class.isAssignableFrom(object.getClass())) {
                HouseBuilder.buildHouse(clazz, map2D, object);
            }

            if (Furniture.class.isAssignableFrom(object.getClass())) {
                FurnitureToHouseAssigner.INSTANCE.assign((Furniture) object);
            }
            if (Floor.class.isAssignableFrom(object.getClass())) {
                FloorToHouseAssigner.INSTANCE.assign((Floor) object);
            }

            if (AnimatedObject.class.isAssignableFrom(clazz))
                AnimationRegistry.INSTANCE.add((AnimatedObject) object, new WorldObjectAnimation((AnimatedObject) object));

            if (LightSource.class.isAssignableFrom(clazz))
                LightSourceRegistry.INSTANCE.add(map2D, (LightSource) object);
        }
        return object;
    }
}
