package com.mygdx.game.registry;

import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.item.Bread;
import com.mygdx.game.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemRegistry {

    public static final ItemRegistry INSTANCE = new ItemRegistry();

    private Map<Map2D, List<Item>> items;

    public ItemRegistry() {
        this.items = new HashMap<>();
    }

    public List<Item> getAllItems(Map2D map) {
        return items.get(map);
    }

    public List<Item> getAllItems(Map2D map, Class clazz) {
        return items.get(map).parallelStream().filter(item -> clazz.isAssignableFrom(item.getClass())).collect(Collectors.toList());

    }

    public void add(Map2D map, Item item) {
        items.computeIfAbsent(map, value -> new ArrayList<>());
        items.get(map).add(item);
    }
    public void remove(Map2D map, Item item) {
        items.get(map).remove(item);
    }

}
