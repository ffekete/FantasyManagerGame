package com.mygdx.game.map.worldmap;

import com.google.common.collect.ImmutableList;
import com.mygdx.game.actor.factory.Placement;
import com.mygdx.game.common.util.MathUtil;
import com.mygdx.game.logic.Point;
import com.mygdx.game.map.dungeon.Dungeon;
import com.mygdx.game.map.dungeon.DungeonGenerator;
import com.mygdx.game.map.dungeon.cave.CaveDungeonCreator;
import com.mygdx.game.map.dungeon.factory.DungeonFactory;
import com.mygdx.game.map.dungeon.room.DungeonWithRoomsCreator;
import com.mygdx.game.object.LinkedWorldObjectFactory;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.interactive.DungeonEntrance;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.registry.TownDataRegistry;

import java.util.List;
import java.util.Random;

public class DungeonEntrancePlacer {

    private List<Class<? extends DungeonGenerator>> generators = ImmutableList.of(
            DungeonWithRoomsCreator.class,
            CaveDungeonCreator.class
    );

    public void place(WorldMap worldMap) {

        WorldObject previous = null;

        for(int i = 0; i < 10; i++) {

            int x,y;
            do {
                x = new Random().nextInt(worldMap.getWidth());
                y = new Random().nextInt(worldMap.getHeight());
            }while (MathUtil.distance(Point.of(x,y), TownDataRegistry.INSTANCE.getTownCenter()) < 20);


            int index = new Random().nextInt(generators.size());
            Dungeon dungeon = DungeonFactory.INSTANCE.create(generators.get(index));

            WorldObject dungeonEntrance = LinkedWorldObjectFactory.INSTANCE.create(DungeonEntrance.class, worldMap, dungeon, ObjectPlacement.FIXED.X(x).Y(y), ObjectPlacement.RANDOM);

            if(previous != null) {
                new RoadCreator().connect(worldMap, previous.getCoordinates(), dungeonEntrance.getCoordinates());
            }

            previous =dungeonEntrance;
        }
    }

}
