package com.mygdx.game.registry;

import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.object.floor.IncompleteStorageAreaFloor;
import com.mygdx.game.object.wall.IncompleteWoodenWall;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ObjectRegistryTest {


    @Test
    public void addAndRemoveBuildingBlock() {
        ObjectRegistry.INSTANCE.clear();
        assertThat(ObjectRegistry.INSTANCE.getBuildingBlock().size(), is(0));

        Map2D map = new DummyDungeonCreator().create(3);
        IncompleteStorageAreaFloor incompleteStorageAreaFloor = new IncompleteStorageAreaFloor(Point.of(3, 3));
        ObjectRegistry.INSTANCE.add(map, Cluster.of(3, 3), incompleteStorageAreaFloor);

        assertThat(ObjectRegistry.INSTANCE.getBuildingBlock().size(), is(1));
        assertThat(ObjectRegistry.INSTANCE.getBuildingBlock().get(0), is(incompleteStorageAreaFloor));
        assertThat(ObjectRegistry.INSTANCE.getObjectGrid().get(map)[3][3][0], is(incompleteStorageAreaFloor));
        assertThat(ObjectRegistry.INSTANCE.getObject(map, IncompleteStorageAreaFloor.class).get().get(0), is(incompleteStorageAreaFloor));

        IncompleteWoodenWall incompleteWoodenWall = new IncompleteWoodenWall(Point.of(3,3));
        ObjectRegistry.INSTANCE.add(map, Cluster.of(3, 3), incompleteWoodenWall);

        assertThat(ObjectRegistry.INSTANCE.getBuildingBlock().size(), is(2));
        assertThat(ObjectRegistry.INSTANCE.getBuildingBlock().get(0), is(incompleteStorageAreaFloor));
        assertThat(ObjectRegistry.INSTANCE.getBuildingBlock().get(1), is(incompleteWoodenWall));
        assertThat(ObjectRegistry.INSTANCE.getObjectGrid().get(map)[3][3][0], is(incompleteStorageAreaFloor));
        assertThat(ObjectRegistry.INSTANCE.getObjectGrid().get(map)[3][3][1], is(incompleteWoodenWall));
        assertThat(ObjectRegistry.INSTANCE.getObject(map, IncompleteStorageAreaFloor.class).get().get(0), is(incompleteStorageAreaFloor));
        assertThat(ObjectRegistry.INSTANCE.getObject(map, IncompleteWoodenWall.class).get().get(0), is(incompleteWoodenWall));

        ObjectRegistry.INSTANCE.remove(map, incompleteWoodenWall);
        assertThat(ObjectRegistry.INSTANCE.getBuildingBlock().size(), is(1));
        assertThat(ObjectRegistry.INSTANCE.getBuildingBlock().get(0), is(incompleteStorageAreaFloor));
        assertThat(ObjectRegistry.INSTANCE.getObjectGrid().get(map)[3][3][0], is(incompleteStorageAreaFloor));
        assertThat(ObjectRegistry.INSTANCE.getObject(map, IncompleteStorageAreaFloor.class).get().get(0), is(incompleteStorageAreaFloor));
    }

}