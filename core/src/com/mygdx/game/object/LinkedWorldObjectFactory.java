package com.mygdx.game.object;

import com.mygdx.game.Config;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.registry.ObjectRegistry;

import java.lang.reflect.InvocationTargetException;

public class LinkedWorldObjectFactory {

    public static final LinkedWorldObjectFactory INSTANCE = new LinkedWorldObjectFactory();

    private final ObjectRegistry objectRegistry = ObjectRegistry.INSTANCE;

    private LinkedWorldObjectFactory() {
    }

    public WorldObject create(Class<? extends WorldObject> clazz, Map2D fromMap, Map2D toMap, ObjectPlacement placement, ObjectPlacement placement2) {
        // this goes to fromMap map
        WorldObject object = null;
        // and this goes to toMap
        WorldObject object2 = null;

        try {
            object = clazz.getDeclaredConstructor(Map2D.class, Map2D.class).newInstance(toMap, fromMap);
            object2 = clazz.getDeclaredConstructor(Map2D.class, Map2D.class).newInstance(fromMap, toMap);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return null;
        }

        placement.place(object, fromMap);
        placement2.place(object2, toMap);

        objectRegistry.add(fromMap,
                new Cluster((int) object.getX() / Config.WorldMap.CLUSTER_DIVIDER,
                        (int) object.getY() / Config.WorldMap.CLUSTER_DIVIDER),
                object);

        objectRegistry.add(toMap,
                new Cluster((int) object2.getX() / Config.WorldMap.CLUSTER_DIVIDER,
                        (int) object2.getY() / Config.WorldMap.CLUSTER_DIVIDER),
                object2);

        return object;
    }
}
