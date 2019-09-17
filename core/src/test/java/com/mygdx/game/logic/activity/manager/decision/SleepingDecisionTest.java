package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.factory.ActorFactory;
import com.mygdx.game.actor.factory.Placement;
import com.mygdx.game.actor.hero.Wizard;
import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.decoration.Tree;
import com.mygdx.game.object.factory.ObjectFactory;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.ObjectRegistry;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class SleepingDecisionTest {


    @Test
    public void test_createCampFire() {

        Map2D worldMap = new DummyDungeonCreator().create(3);
        MapRegistry.INSTANCE.add(worldMap);

        ObjectFactory.create(Tree.class, worldMap, ObjectPlacement.FIXED.X(5).Y(5));
        ObjectFactory.create(Tree.class, worldMap, ObjectPlacement.FIXED.X(6).Y(6));
        ObjectFactory.create(Tree.class, worldMap, ObjectPlacement.FIXED.X(7).Y(5));
        ObjectFactory.create(Tree.class, worldMap, ObjectPlacement.FIXED.X(6).Y(4));

        Actor wizard = ActorFactory.INSTANCE.create(Wizard.class, worldMap, Placement.FIXED.X(5).Y(5));

        Point target = new SleepingDecision().findNextOpenArea(worldMap, 5,5);

        assertThat(target, is(Point.of(3,5)));
    }

    @Test
    public void test_createCampFire2() {

        Map2D worldMap = new DummyDungeonCreator().create(3);
        MapRegistry.INSTANCE.add(worldMap);

        ObjectFactory.create(Tree.class, worldMap, ObjectPlacement.FIXED.X(5).Y(5));
        ObjectFactory.create(Tree.class, worldMap, ObjectPlacement.FIXED.X(6).Y(6));
        ObjectFactory.create(Tree.class, worldMap, ObjectPlacement.FIXED.X(7).Y(5));
        ObjectFactory.create(Tree.class, worldMap, ObjectPlacement.FIXED.X(6).Y(4));

        Actor wizard = ActorFactory.INSTANCE.create(Wizard.class, worldMap, Placement.FIXED.X(6).Y(5));

        Point target = new SleepingDecision().findNextOpenArea(worldMap, 5,5);

        assertThat(target, is(Point.of(3,5)));
    }

    @Test
    public void test_createCampFire_allCovered() {

        Map2D worldMap = new DummyDungeonCreator().create(3);
        MapRegistry.INSTANCE.add(worldMap);

        for (int i = 0; i < worldMap.getWidth(); i++) {
            for (int j = 0; j < worldMap.getHeight(); j++) {
                ObjectFactory.create(Tree.class, worldMap, ObjectPlacement.FIXED.X(i).Y(j));
            }
        }

        Actor wizard = ActorFactory.INSTANCE.create(Wizard.class, worldMap, Placement.FIXED.X(6).Y(5));

        Point target = new SleepingDecision().findNextOpenArea(worldMap, 5,5);

        assertThat(target, is(nullValue()));
    }

    @Test
    public void test_createCampFire_oneSpotIsStillFree() {

        Map2D worldMap = new DummyDungeonCreator().create(3);
        MapRegistry.INSTANCE.add(worldMap);

        for (int i = 0; i < worldMap.getWidth(); i++) {
            for (int j = 0; j < worldMap.getHeight(); j++) {
                ObjectFactory.create(Tree.class, worldMap, ObjectPlacement.FIXED.X(i).Y(j));
            }
        }


        for(int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                WorldObject o = ObjectRegistry.INSTANCE.getObjectGrid().get(worldMap)[30+i][29+j][1];
                ObjectRegistry.INSTANCE.remove(worldMap, o);
            }
        }


        Actor wizard = ActorFactory.INSTANCE.create(Wizard.class, worldMap, Placement.FIXED.X(6).Y(5));

        Point target = new SleepingDecision().findNextOpenArea(worldMap, 5,5);

        assertThat(target, is(Point.of(30, 29)));
    }

}