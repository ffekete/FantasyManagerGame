package com.mygdx.game.registry;

import com.mygdx.game.object.StorageArea;

import java.util.HashSet;
import java.util.Set;

public class StorageRegistry {

    public static final StorageRegistry INSTANCE = new StorageRegistry();

    private Set<StorageArea> storages = new HashSet<>();

    public Set<StorageArea> getStorages() {
        return storages;
    }

    public void add(StorageArea storageArea) {
        this.storages.add(storageArea);
    }
}
