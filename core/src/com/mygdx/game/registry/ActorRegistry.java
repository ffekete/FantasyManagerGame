package com.mygdx.game.registry;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.creator.map.Map2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ActorRegistry {

    public static final ActorRegistry INSTANCE = new ActorRegistry();

    private Map<Map2D, List<Actor>> actors = new ConcurrentHashMap<>();

    public void add(Map2D map, Actor actor) {
        actors.computeIfAbsent(map, value -> new CopyOnWriteArrayList<>());
        actors.get(map).add(actor);
    }

    public List<Actor> getActors(Map2D map) {
        if(!actors.containsKey(map))
            return Collections.emptyList();
        return actors.get(map);
    }
}
