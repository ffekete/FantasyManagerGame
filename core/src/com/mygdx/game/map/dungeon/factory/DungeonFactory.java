package com.mygdx.game.map.dungeon.factory;

import com.mygdx.game.map.dungeon.Dungeon;
import com.mygdx.game.map.dungeon.DungeonGenerator;
import com.mygdx.game.map.dungeon.cave.CaveDungeonDecorator;
import com.mygdx.game.map.dungeon.decorator.ItemPlacementHandler;

public class DungeonFactory {

    public static final DungeonFactory INSTANCE = new DungeonFactory();

    private final CaveDungeonDecorator caveDungeonDecorator = CaveDungeonDecorator.INSTANCE;
    private final ItemPlacementHandler itemPlacementHandler = ItemPlacementHandler.INSTANCE;

    public Dungeon create(Class<? extends DungeonGenerator> clazz) {
        DungeonGenerator dungeonGenerator = null;
        try {
            dungeonGenerator = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        Dungeon dungeon = dungeonGenerator.create(3);

        if (dungeon != null) {
            caveDungeonDecorator.decorate(dungeon);
            itemPlacementHandler.place(dungeon);
        }

        return dungeon;
    }

}
