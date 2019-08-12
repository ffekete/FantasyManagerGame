package com.mygdx.game.object;

import com.mygdx.game.item.Item;

public interface StorageArea extends WorldObject {

    Class<? extends Item> getStoredItem();

    int getStoredAmount();

    void setStoredItem(Class<? extends Item> clazz);

    void setStoredAmount(int amount);

}
