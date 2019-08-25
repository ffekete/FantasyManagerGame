package com.mygdx.game.registry;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.renderer.camera.CameraPositionController;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ActorRegistry {

    public static final ActorRegistry INSTANCE = new ActorRegistry();

    private Map<Map2D, Iterator<Actor>> iterators = new HashMap<>();

    private Map<Map2D, Actor[][]> actorGrid = new HashMap<>();

    private Map<Map2D, Map<Cluster, List<Actor>>> actors = new ConcurrentHashMap<>();

    public void add(Map2D map, Actor actor) {
        actors.computeIfAbsent(map, value -> new HashMap<>());
        actors.get(map).computeIfAbsent(Cluster.of(actor.getX(), actor.getY()), v -> new CopyOnWriteArrayList<>());
        actors.get(map).get(Cluster.of(actor.getX(), actor.getY())).add(actor);

        // actor grid is updated in Placement
    }

    public void remove(Map2D map, Actor actor) {
        actors.get(map).get(Cluster.of(actor.getX(), actor.getY())).remove(actor);

        if(actorGrid.containsKey(map))
            actorGrid.get(map)[actor.getX()][actor.getY()] = null;
    }

    public Optional<Actor> getNext(Map2D map2D) {
        Iterator<Actor> iterator = iterators.getOrDefault(map2D, null);

        boolean freshStart = false;

        if (iterator == null) {
            freshStart = true;
            iterator = getActors(map2D).iterator();
        }

        while (iterator.hasNext() ) {
            Actor actor = iterator.next();
            if (actor.getAlignment().equals(Alignment.FRIENDLY) && !actor.equals(CameraPositionController.INSTANCE.getFocusedOn())) {
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
        return actors.get(map).values().stream().flatMap(actors1 -> actors1.stream()).collect(Collectors.toList());
    }

    public void clear() {
        actors.clear();
    }

    public List<Actor> getAllActors() {
        return actors.values().stream().flatMap(clusters -> clusters.values().stream()).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public boolean containsAnyHeroes(Map2D dungeon) {
        if (!actors.containsKey(dungeon)) {
            return false;
        }

        return (actors.get(dungeon).values().stream().flatMap(clust -> clust.stream()).anyMatch(actor -> Alignment.FRIENDLY.equals(actor.getAlignment())));
    }

    public List<Actor> getActor(Map2D map, Cluster cluster) {
        actors.get(map).computeIfAbsent(cluster, v -> new ArrayList<>());
        return actors.get(map).get(cluster);
    }

    public Actor getActor(Map2D map, int x, int y) {
        return actorGrid.get(map)[x][y];
    }

    public Map<Map2D, Actor[][]> getActorGrid() {
        return actorGrid;
    }
}
