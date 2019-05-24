package com.mygdx.game.object.factory;

import com.mygdx.game.animation.object.WorldObjectAnimation;
import com.mygdx.game.creator.map.Cluster;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.AnimatedObject;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.light.LightSource;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.registry.AnimationRegistry;
import com.mygdx.game.registry.LightSourceRegistry;
import com.mygdx.game.registry.WorldMapObjectRegistry;

import java.lang.reflect.InvocationTargetException;

public class WorldObjectFactory {

    public static <T extends WorldObject> T create(Class<T> clazz, Map2D map2D, ObjectPlacement objectPlacement) {

        T object = null;
        Point point = Point.of(0,0);
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

        if(object != null) {

            objectPlacement.place(object, map2D);

            WorldMapObjectRegistry.INSTANCE.add(Cluster.of(object.getX(), object.getY()), object);

            if(AnimatedObject.class.isAssignableFrom(clazz))
                AnimationRegistry.INSTANCE.add((AnimatedObject)object, new WorldObjectAnimation((AnimatedObject) object));

            if(LightSource.class.isAssignableFrom(clazz))
                LightSourceRegistry.INSTANCE.add(map2D, (LightSource) object);
        }
        return object;
    }

}
