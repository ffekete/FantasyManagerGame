package com.mygdx.game.object;

import com.google.common.collect.ImmutableMap;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.interactive.DungeonEntrance;
import com.mygdx.game.object.interactive.Ladder;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.registry.ObjectRegistry;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class LinkedWorldObjectFactory {

    public static final LinkedWorldObjectFactory INSTANCE = new LinkedWorldObjectFactory();

    private final Map<Class<? extends LinkedWorldObject>, Class<? extends LinkedWorldObject>> linkedObjects = ImmutableMap.<Class<? extends LinkedWorldObject>, Class<? extends LinkedWorldObject>>builder()
            .put(DungeonEntrance.class, Ladder.class)
            .build();

    private final ObjectRegistry objectRegistry = ObjectRegistry.INSTANCE;

    private LinkedWorldObjectFactory() {
    }

    public WorldObject create(Class<? extends LinkedWorldObject> clazz, Map2D fromMap, Map2D toMap, ObjectPlacement placement, ObjectPlacement placement2) {
        // this goes to fromMap map
        LinkedWorldObject object = null;
        // and this goes to toMap
        LinkedWorldObject object2 = null;

        try {
            object = clazz.getDeclaredConstructor(Map2D.class, Map2D.class).newInstance(toMap, fromMap);
            object2 = linkedObjects.get(clazz).getDeclaredConstructor(Map2D.class, Map2D.class).newInstance(fromMap, toMap);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return null;
        }

        object.setExit(object2);
        object2.setExit(object);

        placement.place(object, fromMap);
        placement2.place(object2, toMap);

        toMap.setDefaultSpawningPoint(object2.getCoordinates());

        objectRegistry.add(fromMap,
                Cluster.of((int) object.getX(),
                        (int) object.getY()),
                object);

        objectRegistry.add(toMap,
                Cluster.of((int) object2.getX(),
                        (int) object2.getY()),
                object2);

        return object;
    }
}
