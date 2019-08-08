package com.mygdx.game.map.worldmap;

import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.decoration.Tree;
import com.mygdx.game.object.factory.ObjectFactory;
import com.mygdx.game.object.placement.ObjectPlacement;
import org.testng.annotations.Test;

public class RoadCreatorTest {

    @Test
    public void testForConnect() {
        Map2D map2D = new WorldMapGenerator().create(2);

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                map2D.setTile(i, j, WorldMapTile.GRASS);
                ObjectFactory.create(Tree.class, map2D, ObjectPlacement.FIXED.X(i).Y(j));
            }
        }

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                System.out.print(map2D.isObstacle(i, j) ? 1 : 0);
            }
            System.out.println();
        }

        System.out.println();

        new RoadCreator().connect(map2D, Point.of(1,1), Point.of(15, 19));

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                System.out.print(map2D.isObstacle(i, j) ? 1 : 0);
            }
            System.out.println();
        }
    }

}