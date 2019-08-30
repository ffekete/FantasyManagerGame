package com.mygdx.game.registry;

import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.map.Map2D;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class MapRegistry {

    public static final MapRegistry INSTANCE = new MapRegistry();

    private Map2D currentMapToShow = null;

    private Iterator<Map2D> iterator;

    private List<Map2D> maps;
    private Map<Map2D, PathFinder> pathFinderMap;

    public MapRegistry() {
        maps = new CopyOnWriteArrayList<>();
        pathFinderMap = new HashMap<>();
    }

    public Map2D getNext() {
        if(iterator.hasNext()) {
            return iterator.next();
        } else {
            iterator = maps.iterator();
            return iterator.next();
        }
    }

    public void add(Map2D map) {
        if(maps.isEmpty()) {
            maps.add(map);
            iterator = maps.iterator();
        }
        else {
            maps.add(map);
        }
        PathFinder pathFinder = new PathFinder();
        pathFinder.init(map);
        pathFinderMap.put(map, pathFinder);
    }

    public List<Map2D> getMaps() {
        return maps;
    }

    public Map2D getCurrentMapToShow() {
        return currentMapToShow;
    }

    public void setCurrentMapToShow(Map2D currentMapToShow) {
        this.currentMapToShow = currentMapToShow;
    }

    public PathFinder getPathFinderFor(Map2D map) {
        return pathFinderMap.get(map);
    }
}
