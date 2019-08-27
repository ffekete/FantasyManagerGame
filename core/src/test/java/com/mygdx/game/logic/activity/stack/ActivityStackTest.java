package com.mygdx.game.logic.activity.stack;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.factory.ActorFactory;
import com.mygdx.game.actor.factory.Placement;
import com.mygdx.game.actor.worker.Smith;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.activity.single.SimpleEatingActivity;
import com.mygdx.game.logic.activity.single.SleepActivity;
import com.mygdx.game.logic.time.DayTimeCalculator;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.registry.MapRegistry;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class ActivityStackTest {


    @Test
    public void test() throws InterruptedException {

        Map2D map = new DummyDungeonCreator().create(3);

        Actor actor = ActorFactory.INSTANCE.create(Smith.class, map, Placement.FIXED.X(0).Y(0));

        MapRegistry.INSTANCE.add(map);
        MapRegistry.INSTANCE.setCurrentMapToShow(map);

        actor.getActivityStack().add(new SleepActivity(actor));
        MovementActivity movementActivity = new MovementActivity(actor, 5, 5, 0, MapRegistry.INSTANCE.getPathFinderFor(map));
        movementActivity.suspend();
        actor.getActivityStack().add(movementActivity);

        // fast forward
        DayTimeCalculator.INSTANCE.setActualTime(50000);

        assertThat(actor.getActivityStack().getSize(), is(3));

        for(int i = 0; i < 19; i++)
            actor.getActivityStack().performNext();


        assertThat(actor.getActivityStack().getSize(), is(2));

        // init pathfinder takes some time
        actor.getActivityStack().performNext();
        Thread.sleep(10);

        for(int i = 0; i < actor.getMovementSpeed() * 2 + 10; i++)
            actor.getActivityStack().performNext();

        assertThat(actor.getX(), is(1));
        assertThat(actor.getY(), is(0));
    }

    @Test
    public void testSuspension() {
        Map2D map = new DummyDungeonCreator().create(3);

        Actor actor = ActorFactory.INSTANCE.create(Smith.class, map, Placement.FIXED.X(0).Y(0));

        actor.getActivityStack().clear();

        MapRegistry.INSTANCE.add(map);
        MapRegistry.INSTANCE.setCurrentMapToShow(map);

        MovementActivity movementActivity = mock(MovementActivity.class);
        when(movementActivity.getPriority()).thenReturn(0);


        SleepActivity sleepActivity = mock(SleepActivity.class);
        when(sleepActivity.getPriority()).thenReturn(-1);


        SimpleEatingActivity simpleEatingActivity = mock(SimpleEatingActivity.class);


        when(movementActivity.compareTo(sleepActivity)).thenReturn(1);
        when(movementActivity.compareTo(simpleEatingActivity)).thenReturn(1);

        when(sleepActivity.compareTo(movementActivity)).thenReturn(-1);
        when(sleepActivity.compareTo(simpleEatingActivity)).thenReturn(-1);

        when(simpleEatingActivity.compareTo(sleepActivity)).thenReturn(1);
        when(simpleEatingActivity.compareTo(movementActivity)).thenReturn(-1);


        actor.getActivityStack().add(movementActivity);

        actor.getActivityStack().performNext();

        actor.getActivityStack().add(simpleEatingActivity);

        actor.getActivityStack().add(sleepActivity);

        verify(movementActivity, times(2)).suspend();
        verify(simpleEatingActivity, times(1)).suspend();
        verify(sleepActivity, times(0)).suspend();
    }

}