package com.mygdx.game.registry;

import com.mygdx.game.creator.map.Map2D;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MapRegistry {

    public static final MapRegistry INSTANCE = new MapRegistry();

    private Map2D currentMapToShow = null;

    private List<Map2D> maps;

    public MapRegistry() {
        maps = new CopyOnWriteArrayList<>();
    }

    public void add(Map2D map) {
        maps.add(map);
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
}
