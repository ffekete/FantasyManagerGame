package com.mygdx.game.actor.inventory;

import com.mygdx.game.item.Item;

import java.util.ArrayList;
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

    public <T extends Item> T get(Class<T> clazz) {
        return (T)inventory.parallelStream().filter(item -> clazz.isAssignableFrom(item.getClass())).findFirst().get();
    }

    public boolean has(Class clazz) {
        return inventory.parallelStream().anyMatch(item -> clazz.isAssignableFrom(item.getClass()));
    }

    public long count(Class<? extends Item> clazz) {
        return inventory.stream().filter(item -> clazz.isAssignableFrom(item.getClass())).count();
    }
}
