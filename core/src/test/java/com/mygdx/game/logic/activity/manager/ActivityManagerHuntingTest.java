package com.mygdx.game.logic.activity.manager;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.factory.ActorFactory;
import com.mygdx.game.actor.factory.Placement;
import com.mygdx.game.actor.wildlife.Rabbit;
import com.mygdx.game.actor.wildlife.Wolf;
import com.mygdx.game.logic.activity.single.SimpleAttackActivity;
import com.mygdx.game.logic.activity.single.SleepActivity;
import com.mygdx.game.logic.activity.single.WaitActivity;
import com.mygdx.game.logic.time.DayTimeCalculator;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.worldmap.WorldMapGenerator;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.HouseRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.ObjectRegistry;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ActivityManagerHuntingTest {

    @BeforeMethod
    public void setUp() {
        ActorRegistry.INSTANCE.clear();
        HouseRegistry.INSTANCE.getOwnedHouses().clear();
        HouseRegistry.INSTANCE.getEmptyHouses().clear();
        HouseRegistry.INSTANCE.getHouses().clear();
        ObjectRegistry.INSTANCE.clear();
        ObjectRegistry.INSTANCE.getObjectGrid().clear();
    }

    @Test
    public void testWolfHunts() {

        DayTimeCalculator.INSTANCE.setActualTime(0);

        Map2D map2D = new WorldMapGenerator().create(3);

        Actor wolf = ActorFactory.INSTANCE.create(Wolf.class, map2D, Placement.FIXED.X(2).Y(2));
        Actor rabbit = ActorFactory.INSTANCE.create(Rabbit.class, map2D, Placement.FIXED.X(4).Y(2));

        wolf.increaseHunger(98000);

        MapRegistry.INSTANCE.setCurrentMapToShow(map2D);

        ActivityManager activityManager = new ActivityManager();

        activityManager.manage(wolf);

        assertThat(wolf.getActivityStack().getCurrent().getMainClass(), equalTo(SimpleAttackActivity.class));
    }

    @Test
    public void testWolfDoesntHunt_notHungry() {

        DayTimeCalculator.INSTANCE.setActualTime(0);

        Map2D map2D = new WorldMapGenerator().create(3);

        Actor wolf = ActorFactory.INSTANCE.create(Wolf.class, map2D, Placement.FIXED.X(2).Y(2));
        Actor rabbit = ActorFactory.INSTANCE.create(Rabbit.class, map2D, Placement.FIXED.X(4).Y(2));

        wolf.increaseHunger(0);

        MapRegistry.INSTANCE.setCurrentMapToShow(map2D);

        ActivityManager activityManager = new ActivityManager();

        activityManager.manage(wolf);

        assertThat(wolf.getActivityStack().getCurrent().getMainClass(), equalTo(WaitActivity.class));
    }

    @Test
    public void testWolfSleeps_dayTime_hungry() {

        Map2D map2D = new WorldMapGenerator().create(3);

        Actor wolf = ActorFactory.INSTANCE.create(Wolf.class, map2D, Placement.FIXED.X(2).Y(2));
        Actor rabbit = ActorFactory.INSTANCE.create(Rabbit.class, map2D, Placement.FIXED.X(4).Y(2));

        DayTimeCalculator.INSTANCE.setActualTime(100000);
        assertThat(DayTimeCalculator.INSTANCE.isItNight(), is(false));

        wolf.increaseHunger(98000);

        MapRegistry.INSTANCE.setCurrentMapToShow(map2D);

        ActivityManager activityManager = new ActivityManager();

        activityManager.manage(wolf);

        assertThat(wolf.getActivityStack().getCurrent().getMainClass(), equalTo(SleepActivity.class));
    }

    @Test
    public void testWolfSleeps_dayTime_notHungry() {

        Map2D map2D = new WorldMapGenerator().create(3);

        Actor wolf = ActorFactory.INSTANCE.create(Wolf.class, map2D, Placement.FIXED.X(2).Y(2));
        Actor rabbit = ActorFactory.INSTANCE.create(Rabbit.class, map2D, Placement.FIXED.X(4).Y(2));

        DayTimeCalculator.INSTANCE.setActualTime(100000);
        assertThat(DayTimeCalculator.INSTANCE.isItNight(), is(false));

        wolf.increaseHunger(50000);

        MapRegistry.INSTANCE.setCurrentMapToShow(map2D);

        ActivityManager activityManager = new ActivityManager();

        activityManager.manage(wolf);

        assertThat(wolf.getActivityStack().getCurrent().getMainClass(), equalTo(SleepActivity.class));
    }

}
