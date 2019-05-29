package com.mygdx.game.object;

import com.mygdx.game.Config;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.registry.WorldMapObjectRegistry;

import java.lang.reflect.InvocationTargetException;

public class LinkedWorldObjectFactory {

    public static final LinkedWorldObjectFactory INSTANCE = new LinkedWorldObjectFactory();

    private final WorldMapObjectRegistry worldMapObjectRegistry = WorldMapObjectRegistry.INSTANCE;

    private LinkedWorldObjectFactory() {
    }

    public WorldObject create(Class<? extends WorldObject> clazz, Map2D map, Map2D linkTo, ObjectPlacement placement) {
        WorldObject object = null;
        try {
            object = clazz.getDeclaredConstructor(Map2D.class).newInstance(linkTo);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return null;
        }

        placement.place(object, map);
        worldMapObjectRegistry.add(
                new Cluster((int)object.getX() / Config.WorldMap.CLUSTER_DIVIDER,
                        (int)object.getY() / Config.WorldMap.CLUSTER_DIVIDER),
                object);

        return object;
    }
}
