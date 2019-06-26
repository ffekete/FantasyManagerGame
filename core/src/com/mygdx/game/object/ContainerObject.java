package com.mygdx.game.object;

import com.mygdx.game.item.Item;

import java.util.List;

public interface ContainerObject extends WorldObject {

    void add(Item item);
    void addItems(List<Item> items);
    List<Item> removeItems();
    int getSize();
    boolean isOpened();
    void setMoney(int amount);
    int getMoney();

}
