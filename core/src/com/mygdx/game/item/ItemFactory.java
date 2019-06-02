package com.mygdx.game.item;

import com.mygdx.game.item.factory.Placement;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.ItemRegistry;

public class ItemFactory {

    public Item create(Class<? extends Item> clazz, Map2D map, Placement placement) {
        Item item = null;
        try {
            item = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if(item != null) {
            placement.place(item, map);
            ItemRegistry.INSTANCE.add(map, item);
        }
        return item;
    }

}