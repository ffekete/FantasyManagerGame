package com.mygdx.game.registry;

import com.mygdx.game.creator.map.Cluster;
import com.mygdx.game.object.WorldObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class WorldMapObjectRegistry {

    private final Map<Cluster, Set<WorldObject>> objects;

    public static final WorldMapObjectRegistry INSTANCE = new WorldMapObjectRegistry();

    private WorldMapObjectRegistry() {
        objects = new HashMap<>();
    }

    public void add(Cluster cluster, WorldObject worldObject) {
        objects.computeIfAbsent(cluster, value -> new HashSet<>());
        objects.get(cluster).add(worldObject);
    }

    public Optional<Set<WorldObject>> getObjects(Cluster cluster, Class<? extends WorldObject> clazz) {
        if(!objects.containsKey(cluster)) {
            return Optional.empty();
        }
        return Optional.of(objects.get(cluster).stream().filter(object -> clazz.isAssignableFrom(object.getClass())).collect(Collectors.toSet()));
    }

    public Optional<Set<WorldObject>> getObjects(Cluster cluster) {
        if(!objects.containsKey(cluster)) {
            return Optional.empty();
        }
        return Optional.ofNullable(objects.get(cluster));
    }

}
