package com.mygdx.game.registry;

import com.mygdx.game.builder.BuildingBlock;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.Obstacle;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.floor.Floor;

import java.util.*;
import java.util.stream.Collectors;

public class ObjectRegistry {

    private final Map<Map2D, Map<Cluster, Set<WorldObject>>> objects;
    private Map<Map2D, WorldObject[][][]> objectGrid; // for drawing only

    public static final ObjectRegistry INSTANCE = new ObjectRegistry();

    private ObjectRegistry() {
        objects = new HashMap<>();
        objectGrid = new HashMap<>();
    }

    public List<WorldObject> getAll(Map2D map) {
        return objects.get(map).values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    public void add(Map2D map, Cluster cluster, WorldObject worldObject) {
        objects.computeIfAbsent(map, value -> new HashMap<>());

        objects.get(map).computeIfAbsent(cluster, value -> new HashSet<>());
        objects.get(map).get(cluster).add(worldObject);

        objectGrid.computeIfAbsent(map, v -> new WorldObject[map.getWidth()][map.getHeight()][2]);

        if(Floor.class.isAssignableFrom(worldObject.getClass()))
            objectGrid.get(map)[(int)worldObject.getX()][(int)worldObject.getY()][0] = worldObject;
        else {
            objectGrid.get(map)[(int)worldObject.getX()][(int)worldObject.getY()][1] = worldObject;
        }
    }

    public Optional<List<WorldObject>> getObject(Map2D map, Class<? extends WorldObject> object) {
            return Optional.of(objects.get(map).keySet().stream().flatMap(cluster -> objects.get(map).get(cluster).stream()).filter(object1 -> object.isAssignableFrom(object1.getClass())).collect(Collectors.toList()));
    }

    public Optional<Set<WorldObject>> getObjects(Map2D map, Cluster cluster, Class<? extends WorldObject> clazz) {
        if(!objects.get(map).containsKey(cluster)) {
            return Optional.empty();
        }
        return Optional.of(objects.get(map).get(cluster).stream().filter(object -> clazz.isAssignableFrom(object.getClass())).collect(Collectors.toSet()));
    }

    public Optional<Set<WorldObject>> getObjects(Map2D map, Cluster cluster) {
        if(!objects.containsKey(map) || !objects.get(map).containsKey(cluster)) {
            return Optional.empty();
        }
        return Optional.ofNullable(objects.get(map).get(cluster));
    }

    public void clear() {
        objects.clear();
        objectGrid.clear();
    }

    public void remove(Map2D currentMap, WorldObject object) {
        objects.get(currentMap).get(Cluster.of(object.getX(), object.getY())).remove(object);

        int index = Floor.class.isAssignableFrom(object.getClass()) ? 0 : 1;
        objectGrid.get(currentMap)[(int)object.getX()][(int)object.getY()][index] = null;

        if(Obstacle.class.isAssignableFrom(object.getClass())) {
            currentMap.setObstacle((int)object.getX(), (int)object.getY(), false);
        }

    }

    public Map<Map2D, WorldObject[][][]> getObjectGrid() {
        return objectGrid;
    }
}
