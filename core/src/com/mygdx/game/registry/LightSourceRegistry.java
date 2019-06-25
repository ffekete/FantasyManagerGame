package com.mygdx.game.registry;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.light.LightSource;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class LightSourceRegistry {

    public static final LightSourceRegistry INSTANCE = new LightSourceRegistry();

    private final Map<Map2D, List<LightSource>> lights;
    private final Map<Actor, LightSource> actorLightMap;

    private LightSourceRegistry() {
        this.lights = new HashMap<>();
        this.actorLightMap = new HashMap<>();
    }

    public void add(Actor actor, LightSource lightSource) {
        this.actorLightMap.put(actor, lightSource);
    }

    public void add(Map2D map, LightSource lightSource) {
        lights.computeIfAbsent(map, value -> new CopyOnWriteArrayList<>());
        lights.get(map).add(lightSource);
    }

    public LightSource getFor(Actor actor) {
        return actorLightMap.get(actor);
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

    public void remove(LightSource lightSource) {
        Map2D map = null;
        for(Map.Entry<Map2D, List<LightSource>> entry : this.lights.entrySet()) {
            if(entry.getValue().contains(lightSource)) {
                map = entry.getKey();
                break;
            }
        }
        if(map != null) {
            lights.get(map).remove(lightSource);
        }
    }

    public void remove(Actor actor) {
        this.actorLightMap.remove(actor);
    }
}
