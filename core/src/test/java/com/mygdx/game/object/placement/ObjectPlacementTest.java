package com.mygdx.game.object.placement;

import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.object.factory.ObjectFactory;
import com.mygdx.game.object.floor.IncompleteWoodenFloor;
import com.mygdx.game.object.floor.WoodenFloor;
import com.mygdx.game.object.wall.IncompleteWoodenWall;
import com.mygdx.game.object.wall.WoodenWall;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ObjectPlacementTest {

    @Test
    public void testObstacle() {
        Map2D map = new DummyDungeonCreator().create(3);
        ObjectFactory.create(IncompleteWoodenFloor.class, map, ObjectPlacement.FIXED.X(5).Y(5));
        assertThat(map.isObstacle(5,5), is(false));

        ObjectFactory.create(IncompleteWoodenWall.class, map, ObjectPlacement.FIXED.X(5).Y(5));
        assertThat(map.isObstacle(5,5), is(true));

        ObjectFactory.create(WoodenWall.class, map, ObjectPlacement.FIXED.X(5).Y(5));
        assertThat(map.isObstacle(5,5), is(true));

        ObjectFactory.create(WoodenFloor.class, map, ObjectPlacement.FIXED.X(5).Y(5));
        assertThat(map.isObstacle(5,5), is(true));
    }


}