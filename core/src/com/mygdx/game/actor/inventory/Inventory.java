package com.mygdx.game.actor.inventory;

import com.mygdx.game.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Inventory {
    private final List<Item> inventory;

    public Inventory() {
        inventory = new ArrayList<>();
    }

    public void add(Item item) {
        inventory.add(item);
    }

    public void remove(Item item) {
        inventory.remove(item);
    }

    public boolean has(Class clazz) {
        return inventory.parallelStream().anyMatch(item -> clazz.isAssignableFrom(item.getClass()));
    }
}
