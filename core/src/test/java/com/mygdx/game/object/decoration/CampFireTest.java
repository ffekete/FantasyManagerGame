package com.mygdx.game.object.decoration;

import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.object.factory.ObjectFactory;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.registry.MapRegistry;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class CampFireTest {

    @Test
    public void placement_noObstacles() {
        Map2D worldMap = new DummyDungeonCreator().create(3);
        MapRegistry.INSTANCE.add(worldMap);

        // o o o o o o o
        // o o o o o o o
        // o o o o o o o
        // o o o o o x o
        // o o o o x * x
        // o o o o o x o
        // o o o o o o o

        ObjectFactory.create(Tree.class, worldMap, ObjectPlacement.FIXED.X(5).Y(5));
        ObjectFactory.create(Tree.class, worldMap, ObjectPlacement.FIXED.X(6).Y(6));
        ObjectFactory.create(Tree.class, worldMap, ObjectPlacement.FIXED.X(7).Y(5));
        ObjectFactory.create(Tree.class, worldMap, ObjectPlacement.FIXED.X(6).Y(4));
        CampFire campFire = ObjectFactory.create(CampFire.class, worldMap, ObjectPlacement.FIXED.X(6).Y(5));

        assertThat(campFire, is(notNullValue()));
        assertThat(campFire.hasFreeSpace(), is(true));

        assertThat(campFire.getNextFreeSpace(), is(Point.of(5,5)));
        campFire.bookSpace(campFire.getNextFreeSpace());

        assertThat(campFire.getNextFreeSpace(), is(Point.of(6,6)));
        campFire.bookSpace(campFire.getNextFreeSpace());

        assertThat(campFire.getNextFreeSpace(), is(Point.of(7,5)));
        campFire.bookSpace(campFire.getNextFreeSpace());

        assertThat(campFire.getNextFreeSpace(), is(Point.of(6,4)));
        campFire.bookSpace(campFire.getNextFreeSpace());

        assertThat(campFire.hasFreeSpace(), is(false));
        assertThat(campFire.getNextFreeSpace(), is(nullValue()));
    }

    @Test
    public void placement_allObstacles() {
        Map2D worldMap = new DummyDungeonCreator().create(3);
        MapRegistry.INSTANCE.add(worldMap);

        ObjectFactory.create(Rock.class, worldMap, ObjectPlacement.FIXED.X(5).Y(5));
        ObjectFactory.create(Rock.class, worldMap, ObjectPlacement.FIXED.X(6).Y(6));
        ObjectFactory.create(Rock.class, worldMap, ObjectPlacement.FIXED.X(7).Y(5));
        ObjectFactory.create(Rock.class, worldMap, ObjectPlacement.FIXED.X(6).Y(4));
        CampFire campFire = ObjectFactory.create(CampFire.class, worldMap, ObjectPlacement.FIXED.X(6).Y(5));

        assertThat(campFire, is(notNullValue()));
        assertThat(campFire.hasFreeSpace(), is(false));
    }

    @Test
    public void placement_someObstacles() {
        Map2D worldMap = new DummyDungeonCreator().create(3);
        MapRegistry.INSTANCE.add(worldMap);

        ObjectFactory.create(Tree.class, worldMap, ObjectPlacement.FIXED.X(5).Y(5));
        ObjectFactory.create(Rock.class, worldMap, ObjectPlacement.FIXED.X(6).Y(6));
        ObjectFactory.create(Tree.class, worldMap, ObjectPlacement.FIXED.X(7).Y(5));
        ObjectFactory.create(Rock.class, worldMap, ObjectPlacement.FIXED.X(6).Y(4));
        CampFire campFire = ObjectFactory.create(CampFire.class, worldMap, ObjectPlacement.FIXED.X(6).Y(5));

        assertThat(campFire, is(notNullValue()));
        assertThat(campFire.hasFreeSpace(), is(true));

        assertThat(campFire.getNextFreeSpace(), is(Point.of(5,5)));
        campFire.bookSpace(campFire.getNextFreeSpace());

        //assertThat(campFire.getNextFreeSpace(), is(Point.of(6,6)));
        //campFire.bookSpace(campFire.getNextFreeSpace());

        assertThat(campFire.getNextFreeSpace(), is(Point.of(7,5)));
        campFire.bookSpace(campFire.getNextFreeSpace());

        //assertThat(campFire.getNextFreeSpace(), is(Point.of(6,4)));
        //campFire.bookSpace(campFire.getNextFreeSpace());

        assertThat(campFire.hasFreeSpace(), is(false));
        assertThat(campFire.getNextFreeSpace(), is(nullValue()));

        campFire.freeUp(Point.of(7,5));

        assertThat(campFire.getNextFreeSpace(), is(Point.of(7,5)));
        campFire.bookSpace(campFire.getNextFreeSpace());

        assertThat(campFire.hasFreeSpace(), is(false));
        assertThat(campFire.getNextFreeSpace(), is(nullValue()));
    }

}