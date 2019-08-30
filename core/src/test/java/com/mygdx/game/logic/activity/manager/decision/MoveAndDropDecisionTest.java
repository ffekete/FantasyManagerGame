package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.worker.Builder;
import com.mygdx.game.item.resources.Wood;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.single.DropItemActivity;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.worldmap.WorldMap;
import com.mygdx.game.object.factory.ObjectFactory;
import com.mygdx.game.object.floor.StorageAreaFloor;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.registry.StorageRegistry;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class MoveAndDropDecisionTest {

    @Test
    public void performanceTest() {
        StorageRegistry.INSTANCE.getStorages().clear();

        Map2D map = new WorldMap(5000, 1);

        for (int i = 0; i < 5000; i++) {
            StorageAreaFloor s = ObjectFactory.create(StorageAreaFloor.class, map, ObjectPlacement.FIXED.X(i).Y(0));
        }

        Actor actor = new Builder();
        actor.setCurrentMap(map);
        actor.getInventory().add(new Wood(Point.of(1,1)));

        Decision moveAndDropDecision = new MoveAndDropDecision();
        long start = System.currentTimeMillis();
        moveAndDropDecision.decide(actor);
        long duration = System.currentTimeMillis() - start;

        assertThat(actor.getActivityStack().contains(DropItemActivity.class), is(true));
        assertThat(duration, is(lessThan(80L)));
    }

}