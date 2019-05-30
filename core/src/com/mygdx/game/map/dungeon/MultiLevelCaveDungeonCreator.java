package com.mygdx.game.map.dungeon;

import com.mygdx.game.Config;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.cave.CaveDungeonCreator;
import com.mygdx.game.object.factory.ObjectFactory;
import com.mygdx.game.object.interactive.DungeonEntrance;
import com.mygdx.game.object.placement.ObjectPlacement;

import java.util.Random;

public class MultiLevelCaveDungeonCreator implements MapGenerator {

    private final CaveDungeonCreator caveDungeonCreator = new CaveDungeonCreator();

    @Override
    public Map2D create(int steps) {
        int size = new Random().nextInt(Config.Dungeon.MULTILEVEL_DUNGEON_MAX_SIZE - Config.Dungeon.MULTILEVEL_DUNGEON_MIN_SIZE) + Config.Dungeon.MULTILEVEL_DUNGEON_MIN_SIZE;


        Dungeon actualLevel = caveDungeonCreator.create(0);
        Dungeon topLevel = actualLevel;
        for (int i = 0; i < size; i++) {
            Dungeon nextLevel = caveDungeonCreator.create(0);

            actualLevel.setNextLevel(nextLevel);
            nextLevel.setPreviousLevel(actualLevel);

            DungeonEntrance dungeonEntrance = ObjectFactory.create(DungeonEntrance.class, nextLevel, ObjectPlacement.RANDOM);

            actualLevel = nextLevel;
        }
        return topLevel;
    }
}
