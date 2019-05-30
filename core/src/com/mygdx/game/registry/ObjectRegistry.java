package com.mygdx.game.registry;

import com.mygdx.game.map.Cluster;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.WorldObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ObjectRegistry {

    private final Map<Map2D, Map<Cluster, Set<WorldObject>>> objects;

    public static final ObjectRegistry INSTANCE = new ObjectRegistry();

    private ObjectRegistry() {
        objects = new HashMap<>();
    }

    public void add(Map2D map, Cluster cluster, WorldObject worldObject) {
        objects.computeIfAbsent(map, value -> new HashMap<>());
        objects.get(map).computeIfAbsent(cluster, value -> new HashSet<>());
        objects.get(map).get(cluster).add(worldObject);
    }

    public Optional<List<WorldObject>> getObject(Map2D map, Class<? extends WorldObject> object) {
            return Optional.of(objects.get(map).keySet().stream().flatMap(cluster -> objects.get(map).get(cluster).stream()).filter(object1 -> object1.getClass().equals(object)).collect(Collectors.toList()));
    }

    public Optional<Set<WorldObject>> getObjects(Map2D map, Cluster cluster, Class<? extends WorldObject> clazz) {
        if(!objects.get(map).containsKey(cluster)) {
            return Optional.empty();
        }
        return Optional.of(objects.get(map).get(cluster).stream().filter(object -> clazz.isAssignableFrom(object.getClass())).collect(Collectors.toSet()));
    }

    public Optional<Set<WorldObject>> getObjects(Map2D map, Cluster cluster) {
        if(!objects.get(map).containsKey(cluster)) {
            return Optional.empty();
        }
        return Optional.ofNullable(objects.get(map).get(cluster));
    }

}
