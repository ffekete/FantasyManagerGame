package com.mygdx.game.registry;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.renderer.camera.CameraPositionController;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ActorRegistry {

    private Map<Integer, Map<Map2D, List<Actor>>> bucket;
    private Map<Actor, Integer> bucketInverse;
    private int bucketIndex = 0;


    public static final ActorRegistry INSTANCE = new ActorRegistry();

    public ActorRegistry() {
        this.bucketInverse = new HashMap<>();
        this.bucket = new HashMap<>();
    }

    private Map<Map2D, Iterator<Actor>> iterators = new HashMap<>();

    private Map<Map2D, Actor[][]> actorGrid = new HashMap<>();

    private Map<Map2D, Map<Cluster, List<Actor>>> actors = new ConcurrentHashMap<>();

    public void add(Map2D map, Actor actor) {
        add(map, actor, true);
    }

    public void add(Map2D map, Actor actor, boolean updateBucket) {
        actors.computeIfAbsent(map, value -> new HashMap<>());
        actors.get(map).computeIfAbsent(Cluster.of(actor.getX(), actor.getY()), v -> new CopyOnWriteArrayList<>());
        actors.get(map).get(Cluster.of(actor.getX(), actor.getY())).add(actor);
        if(updateBucket)
            addToBucket(actor, map);
        // actor grid is updated in Placement
    }

    public void remove(Map2D map, Actor actor, boolean updateBucket) {
        if(actors.containsKey(map) && actors.get(map).containsKey(Cluster.of(actor.getX(), actor.getY())))
            actors.get(map).get(Cluster.of(actor.getX(), actor.getY())).remove(actor);

        if(actorGrid.containsKey(map))
            actorGrid.get(map)[actor.getX()][actor.getY()] = null;

        if(updateBucket && bucket.containsKey(bucketInverse.get(actor)) && bucketInverse.containsKey(actor)) {
            bucket.get(bucketInverse.get(actor)).remove(actor);
            bucketInverse.remove(actor);
        }
    }

    void addToBucket(Actor actor, Map2D map2D) {
        bucket.computeIfAbsent(bucketIndex, v -> new HashMap<>());
        bucket.get(bucketIndex).computeIfAbsent(map2D, v -> new ArrayList<>());

        bucket.get(bucketIndex).get(map2D).add(actor);
        bucketInverse.put(actor, bucketIndex);
        bucketIndex = (bucketIndex + 1) % Config.Engine.BUCKET_SIZE;
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

    public List<Actor> getBucketedActors(Map2D map, int index) {
        if (!bucket.containsKey(index) || !bucket.get(index).containsKey(map))
            return Collections.emptyList();

        return bucket.get(index).get(map);
    }

    public List<Actor> getActors(Map2D map) {
        if (!actors.containsKey(map))
            return Collections.emptyList();
        return actors.get(map).values().stream().flatMap(actors1 -> actors1.stream()).collect(Collectors.toList());
    }

    public void clear() {
        actors.clear();
        actorGrid.clear();
        bucket.clear();
        bucketInverse.clear();
        bucketIndex = 0;
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
