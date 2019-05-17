package com.mygdx.game.registry;

import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.object.light.LightSource;

import java.util.*;

public class LightSourceRegistry {

    public static final LightSourceRegistry INSTANCE = new LightSourceRegistry();

    private final Map<Map2D, List<LightSource>> lights;

    private LightSourceRegistry() {
        this.lights = new HashMap<>();
    }

    public void add(Map2D map, LightSource lightSource) {
        lights.computeIfAbsent(map, value -> new ArrayList<>());
        lights.get(map).add(lightSource);
    }

    public List<LightSource> getFor(Map2D map) {
        if(lights.containsKey(map))
            return lights.get(map);
        else
            return Collections.emptyList();
    }

    public void remove(Map2D map, LightSource lightSource) {
        lights.computeIfAbsent(map, value -> new ArrayList<>());
        lights.get(map).remove(lightSource);
    }
}
