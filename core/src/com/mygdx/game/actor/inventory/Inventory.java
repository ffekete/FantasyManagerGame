package com.mygdx.game.actor.inventory;

import com.mygdx.game.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public boolean has(Item item) {
        return inventory.parallelStream().anyMatch(item::equals);
    }

    public boolean has(Class clazz) {
        return inventory.parallelStream().anyMatch(item -> clazz.isAssignableFrom(item.getClass()));
    }

    public Optional<Item> get(int index) {
        if(inventory.size() <= index)
            return Optional.empty();

        return Optional.ofNullable(inventory.get(index));
    }

    public int size() {
        return inventory.size();
    }

    public long count(Class<? extends Item> clazz) {
        return inventory.stream().filter(item -> clazz.isAssignableFrom(item.getClass())).count();
    }
}
