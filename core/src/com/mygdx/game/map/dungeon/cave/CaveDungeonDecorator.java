package com.mygdx.game.map.dungeon.cave;

import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.Decorator;
import com.mygdx.game.map.dungeon.decorator.Decoration;
import com.mygdx.game.utils.MapUtils;

public class CaveDungeonDecorator implements Decorator {

    public static final CaveDungeonDecorator INSTANCE = new CaveDungeonDecorator();

    @Override
    public void decorate(Map2D map) {
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {

                int mask = MapUtils.bitmask4bit(map, i,j);

                for(Decoration decoration : Decoration.values()) {
                    if(decoration.place(map, mask, i,j)) {
                        break;
                    }
                }
            }
        }
    }

    private CaveDungeonDecorator() {
    }
}
