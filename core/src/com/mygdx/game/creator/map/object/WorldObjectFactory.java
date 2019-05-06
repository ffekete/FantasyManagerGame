package com.mygdx.game.creator.map.object;

import com.mygdx.game.Config;
import com.mygdx.game.creator.map.Cluster;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.creator.map.object.factory.ObjectPlacement;
import com.mygdx.game.registry.WorldMapObjectRegistry;

public class WorldObjectFactory {

    public static final WorldObjectFactory INSTANCE = new WorldObjectFactory();

    private final WorldMapObjectRegistry worldMapObjectRegistry = WorldMapObjectRegistry.INSTANCE;

    private WorldObjectFactory() {
    }

    public WorldObject create(Class<? extends WorldObject> clazz, Map2D map, ObjectPlacement placement) {
        WorldObject object = null;
        try {
            object = clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return null;
        }

        placement.place(object, map);
        worldMapObjectRegistry.add(
                new Cluster(object.getX() / Config.WorldMap.CLUSTER_DIVIDER,
                        object.getY() / Config.WorldMap.CLUSTER_DIVIDER),
                object);

        return object;
    }
}
