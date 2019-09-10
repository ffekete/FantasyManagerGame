package com.mygdx.game.map.dungeon.factory;

import com.mygdx.game.map.worldmap.WorldMap;
import com.mygdx.game.map.worldmap.WorldMapDecorator;
import com.mygdx.game.map.worldmap.WorldMapGenerator;
import com.mygdx.game.registry.MapRegistry;

public class WorldMapFactory {

    public static final WorldMapFactory INSTANCE = new WorldMapFactory();

    public WorldMap create() {
        // create
        WorldMap worldMap = new WorldMapGenerator().create(0);
        MapRegistry.INSTANCE.add(worldMap);

        // decorate
        WorldMapDecorator worldMapDecorator = new WorldMapDecorator();
        worldMapDecorator.decorate(2, worldMap);

        return worldMap;
    }

}
