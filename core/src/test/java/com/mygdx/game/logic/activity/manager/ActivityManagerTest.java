package com.mygdx.game.logic.activity.manager;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.monster.Goblin;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.compound.MoveThenAttackActivity;
import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ActivityManagerTest {

    @Test
    public void testFindClosestEnemy() throws InterruptedException {
        ActivityManager activityManager = new ActivityManager();

        VisibilityMask visibilityMask = new VisibilityMask(100, 100);
        Map2D dungeon = new DummyDungeonCreator().create(5);
        VisibilityMapRegistry.INSTANCE.add(dungeon, visibilityMask);

        Actor hero = new Warrior();
        hero.setCoordinates(new Point(10, 10));
        hero.setCurrentMap(dungeon);
        Actor goblin = new Goblin();
        goblin.setCoordinates(new Point(15, 15));
        goblin.setCurrentMap(dungeon);

        visibilityMask.setValue(15,15, hero);
        visibilityMask.setValue(10,10, goblin);

        ActorRegistry.INSTANCE.add(dungeon, hero);
        ActorRegistry.INSTANCE.add(dungeon, goblin);
        MapRegistry.INSTANCE.add(dungeon);

        activityManager.manage(hero);
        activityManager.manage(goblin);

        assertThat(hero.getActivityStack().contains(MoveThenAttackActivity.class), is(true));
        assertThat(goblin.getActivityStack().contains(MoveThenAttackActivity.class), is(true));

        // perform next task for goblin
        for(int i = 0; i <= goblin.getMovementSpeed() * 2; i++) {
            goblin.getActivityStack().performNext();
            Thread.sleep(15); // this is needed for pathfinder to generate the path in time
        }
        assertThat(goblin.getX(), is(15));
        assertThat(goblin.getY(), is(15));

        // perform next task for hero
        for(int i = 0; i <= hero.getMovementSpeed() * 2; i++) {
            hero.getActivityStack().performNext();
            Thread.sleep(5); // this is needed for pathfinder to generate the path in time
        }

        //for(int i = 0; i < hero.getAttackSpeed(); i++)
            //hero.getActivityStack().performNext();

        assertThat(hero.getX(), is(11));
        assertThat(hero.getY(), is(11));

    }

}