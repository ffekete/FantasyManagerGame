package com.mygdx.game.registry;

import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.map.Map2D;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VisibilityMapRegistry {

    public static final VisibilityMapRegistry INSTANCE = new VisibilityMapRegistry();

    private Map<Map2D, VisibilityMask> registry;

    public VisibilityMapRegistry() {
        registry = new ConcurrentHashMap<>();
    }

    public void add(Map2D map, VisibilityMask mask) {
        registry.put(map, mask);
    }

    public VisibilityMask getFor(Map2D map) {
        registry.computeIfAbsent(map, value -> new VisibilityMask(map.getWidth(), map.getHeight()));
        if(registry.containsKey(map)) {
            return registry.get(map);
        }
        return null;
    }
}
