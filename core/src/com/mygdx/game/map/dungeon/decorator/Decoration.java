package com.mygdx.game.map.dungeon.decorator;

import com.mygdx.game.Config;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.category.Tier1;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.factory.ObjectFactory;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.registry.ItemRegistry;

import java.util.Random;

public enum Decoration implements DecorationItem {

    SpiderWeb {
        @Override
        public boolean place(Map2D map, int mask, int x, int y) {
            if(mask == 3 && !map.isObstacle(x,y) && new Random().nextInt(Config.Dungeon.WORLD_OBJECT_SPAWN_RATE) == 0) {
                ObjectFactory.create(com.mygdx.game.object.decoration.SpiderWeb.class, map, ObjectPlacement.FIXED.X(x).Y(y));
                return true;
            }
            return false;
        }
    },
    TreasureChest {
        @Override
        public boolean place(Map2D map, int mask, int x, int y) {
            if(mask == 0 && !map.isObstacle(x,y) && new Random().nextInt(Config.Dungeon.CHEST_SPAWN_RATE) == 0) {
                com.mygdx.game.object.decoration.TreasureChest treasureChest = ObjectFactory.create(com.mygdx.game.object.decoration.TreasureChest.class, map, ObjectPlacement.FIXED.X(x).Y(y));

                for(int i = 0; i < new Random().nextInt(2) + 1; i++) {
                    Class<? extends Item> itemClass = ItemRegistry.INSTANCE.getFor(Tier1.class).get(new Random().nextInt(ItemRegistry.INSTANCE.getFor(Tier1.class).size()));
                    try {
                        treasureChest.add(itemClass.newInstance());
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                treasureChest.setMoney(new Random().nextInt(20) + 10);

                return true;
            }
            return false;
        }
    }

}
