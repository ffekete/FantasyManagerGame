package com.mygdx.game.logic.activity.manager;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.factory.ActorFactory;
import com.mygdx.game.actor.factory.Placement;
import com.mygdx.game.actor.worker.Smith;
import com.mygdx.game.logic.activity.single.SleepActivity;
import com.mygdx.game.logic.activity.single.SmithingActivity;
import com.mygdx.game.logic.time.DayTimeCalculator;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.map.worldmap.WorldMap;
import com.mygdx.game.object.factory.HouseFactory;
import com.mygdx.game.object.factory.ObjectFactory;
import com.mygdx.game.object.interactive.Anvil;
import com.mygdx.game.object.interactive.Smelter;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.HouseRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.ObjectRegistry;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ActivityManagerSmithTest {

    @BeforeMethod
    public void setUp() {
        ActorRegistry.INSTANCE.clear();
        HouseRegistry.INSTANCE.getOwnedHouses().clear();
        HouseRegistry.INSTANCE.getEmptyHouses().clear();
        HouseRegistry.INSTANCE.getHouses().clear();
        ObjectRegistry.INSTANCE.clear();
        ObjectRegistry.INSTANCE.getObjectGrid().clear();
        DayTimeCalculator.INSTANCE.setActualTime(0);
    }

    @Test
    public void testMovementAndSmithing() {

        Map2D map2D = new DummyDungeonCreator().create(3);
        Actor actor = ActorFactory.INSTANCE.create(Smith.class, map2D, Placement.FIXED.X(2).Y(2));

        MapRegistry.INSTANCE.setCurrentMapToShow(map2D);

        HouseFactory.INSTANCE.create(1,1, 5, map2D);

        ActivityManager activityManager = new ActivityManager();

        ObjectFactory.create(Anvil.class, map2D, ObjectPlacement.FIXED.X(2).Y(4));
        ObjectFactory.create(Smelter.class, map2D, ObjectPlacement.FIXED.X(2).Y(5));

        activityManager.manage(actor);

        assertThat(actor.getActivityStack().getCurrent().getMainClass(), equalTo(SmithingActivity.class));
    }

    @Test
    public void testMovementAndSmithing_sleepInstead() {

        DayTimeCalculator.INSTANCE.setActualTime(0);

        assertThat(DayTimeCalculator.INSTANCE.isItNight(), is(true));

        Map2D map2D = new WorldMap(300, 300);
        Actor actor = ActorFactory.INSTANCE.create(Smith.class, map2D, Placement.FIXED.X(2).Y(2));

        MapRegistry.INSTANCE.setCurrentMapToShow(map2D);

        HouseFactory.INSTANCE.create(1,1, 5, map2D);

        ActivityManager activityManager = new ActivityManager();

        ObjectFactory.create(Anvil.class, map2D, ObjectPlacement.FIXED.X(2).Y(4));
        ObjectFactory.create(Smelter.class, map2D, ObjectPlacement.FIXED.X(2).Y(5));

        activityManager.manage(actor);

        assertThat(actor.getActivityStack().getCurrent().getMainClass(), equalTo(SleepActivity.class));
    }

    @Test
    public void testMovementAndSmithing_smithingThenSleep() throws InterruptedException {

        DayTimeCalculator.INSTANCE.setActualTime(100000);

        assertThat(DayTimeCalculator.INSTANCE.isItNight(), is(false));

        Map2D map2D = new WorldMap(300, 300);
        Actor actor = ActorFactory.INSTANCE.create(Smith.class, map2D, Placement.FIXED.X(2).Y(2));

        MapRegistry.INSTANCE.setCurrentMapToShow(map2D);

        HouseFactory.INSTANCE.create(1,1, 5, map2D);

        ActivityManager activityManager = new ActivityManager();

        ObjectFactory.create(Anvil.class, map2D, ObjectPlacement.FIXED.X(2).Y(4));
        ObjectFactory.create(Smelter.class, map2D, ObjectPlacement.FIXED.X(2).Y(5));

        activityManager.manage(actor);

        assertThat(actor.getActivityStack().getSize(), is(2));
        assertThat(actor.getActivityStack().getCurrent().getMainClass(), equalTo(SmithingActivity.class));

        DayTimeCalculator.INSTANCE.setActualTime(0);

        activityManager.manage(actor);

        assertThat(actor.getActivityStack().getCurrent().getMainClass(), equalTo(SleepActivity.class));

        activityManager.manage(actor);

        assertThat(actor.getActivityStack().getSize(), is(3));
        assertThat(actor.getActivityStack().getCurrent().getMainClass(), equalTo(SleepActivity.class));
    }

}
