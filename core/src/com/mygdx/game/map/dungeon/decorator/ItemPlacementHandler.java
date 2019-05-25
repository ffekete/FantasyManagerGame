package com.mygdx.game.map.dungeon.decorator;

import com.mygdx.game.item.Item;
import com.mygdx.game.item.ItemFactory;
import com.mygdx.game.item.category.Tier1;
import com.mygdx.game.item.factory.Placement;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.ItemRegistry;

import java.util.List;
import java.util.Random;

public class ItemPlacementHandler {

    public static final ItemPlacementHandler INSTANCE = new ItemPlacementHandler();

    private final ItemRegistry itemRegistry = ItemRegistry.INSTANCE;
    private final ItemFactory itemFactory = new ItemFactory();

    public void place(Map2D map) {

        // place Tier1 items

        List<Class<? extends Item>> tier1Items = itemRegistry.getFor(Tier1.class);

        for(int i = 0; i < 10; i++) {
            int r = new Random().nextInt(tier1Items.size());
            itemFactory.create(tier1Items.get(r), map, Placement.RANDOM);
        }

    }

}
