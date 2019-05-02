package com.mygdx.game.registry;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.faction.Alignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class ActorRegistry {

    public static final ActorRegistry INSTANCE = new ActorRegistry();

    private Map<Map2D, Set<Actor>> actors = new ConcurrentHashMap<>();

    public void add(Map2D map, Actor actor) {
        actors.computeIfAbsent(map, value -> new CopyOnWriteArraySet<>());
        actors.get(map).add(actor);
    }

    public void setActors(Map<Map2D, Set<Actor>> actors) {
        this.actors = actors;
    }

    public Set<Actor> getActors(Map2D map) {
        if(!actors.containsKey(map))
            return Collections.emptySet();
        return actors.get(map);
    }

    public boolean containsAnyHeroes(Map2D dungeon) {
        if(!actors.containsKey(dungeon)) {
            return false;
        }

        return (actors.get(dungeon).stream().anyMatch(actor -> Alignment.FRIENDLY.equals(actor.getAlignment())));
    }
}
