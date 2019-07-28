package com.mygdx.game.registry;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.faction.Alignment;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ActorRegistry {

    public static final ActorRegistry INSTANCE = new ActorRegistry();

    private Map<Map2D, Iterator<Actor>> iterators = new HashMap<>();

    private Map<Map2D, List<Actor>> actors = new ConcurrentHashMap<>();

    public void add(Map2D map, Actor actor) {
        actors.computeIfAbsent(map, value -> new CopyOnWriteArrayList<>());
        actors.get(map).add(actor);
    }

    public void remove(Map2D map, Actor actor) {
        actors.get(map).remove(actor);
    }

    public void setActors(Map<Map2D, List<Actor>> actors) {
        this.actors = actors;
    }

    public Optional<Actor> getNext(Map2D map2D) {
        Iterator<Actor> iterator = iterators.getOrDefault(map2D, null);

        boolean freshStart = false;

        if (iterator == null) {
            freshStart = true;
            iterator = getActors(map2D).iterator();
        }
        while (iterator.hasNext()) {
            Actor actor = iterator.next();
            if (actor.getAlignment().equals(Alignment.FRIENDLY)) {
                iterators.put(map2D, iterator);
                return Optional.of(actor);
            }
        }

        if (!freshStart) {
            iterator = getActors(map2D).iterator();
            while (iterator.hasNext()) {
                Actor actor = iterator.next();
                if (actor.getAlignment().equals(Alignment.FRIENDLY)) {
                    iterators.put(map2D, iterator);
                    return Optional.of(actor);
                }
            }
        }

        iterators.remove(map2D);
        return Optional.empty();
    }

    public List<Actor> getActors(Map2D map) {
        if (!actors.containsKey(map))
            return Collections.emptyList();
        return actors.get(map);
    }

    public List<Actor> getAllActors() {
        return actors.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    public boolean containsAnyHeroes(Map2D dungeon) {
        if (!actors.containsKey(dungeon)) {
            return false;
        }

        return (actors.get(dungeon).stream().anyMatch(actor -> Alignment.FRIENDLY.equals(actor.getAlignment())));
    }
}
