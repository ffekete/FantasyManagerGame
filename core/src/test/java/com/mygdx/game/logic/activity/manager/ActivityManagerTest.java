package com.mygdx.game.logic.activity.manager;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.monster.Goblin;
import com.mygdx.game.actor.monster.Skeleton;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.activity.single.PreCalculatedMovementActivity;
import com.mygdx.game.logic.actor.ActorMovementHandler;
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

    private final ActorMovementHandler actorMovementHandler = ActorMovementHandler.INSTANCE;

    @Test
    public void testFindClosestEnemyAndMove() throws InterruptedException {
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

        Actor skeleton = new Skeleton();
        skeleton.setCoordinates(Point.of(0,0));
        skeleton.setCurrentMap(dungeon);

        visibilityMask.setValue(15,15, hero);
        visibilityMask.setValue(10,10, goblin);
        visibilityMask.setValue(10,10, skeleton);

        ActorRegistry.INSTANCE.add(dungeon, hero);
        ActorRegistry.INSTANCE.add(dungeon, goblin);
        ActorRegistry.INSTANCE.add(dungeon, skeleton);
        MapRegistry.INSTANCE.add(dungeon);

        activityManager.manage(hero);
        activityManager.manage(goblin);

        assertThat(hero.getActivityStack().contains(MoveThenAttackActivity.class), is(true));
        assertThat(goblin.getActivityStack().contains(MoveThenAttackActivity.class), is(true));

        // perform next task for goblin
        for(int i = 0; i <= goblin.getMovementSpeed() * 3; i++) {
            goblin.getActivityStack().performNext();
            Thread.sleep(5); // this is needed for pathfinder to generate the path in time
        }
        assertThat(goblin.getX(), is(14));
        assertThat(goblin.getY(), is(14));

        activityManager.manage(skeleton);
        assertThat(skeleton.getActivityStack().contains(MoveThenAttackActivity.class), is(true));


        // perform next task for skeleton
        for(int i = 0; i <= skeleton.getMovementSpeed() * 3; i++) {
            skeleton.getActivityStack().performNext();
            Thread.sleep(5); // this is needed for pathfinder to generate the path in time
        }
        assertThat(skeleton.getX(), is(2));
        assertThat(skeleton.getY(), is(0));

        // check target for goblin
        MoveThenAttackActivity moveThenAttackActivity = (MoveThenAttackActivity) goblin.getActivityStack().getCurrent();
        PreCalculatedMovementActivity movementActivity = (PreCalculatedMovementActivity) moveThenAttackActivity.getCurrentActivity();
        assertThat(movementActivity.getTargetX(), is(13));
        assertThat(movementActivity.getTargetY(), is(13));

        // check target for warrior
        MoveThenAttackActivity moveThenAttackActivity2 = (MoveThenAttackActivity) hero.getActivityStack().getCurrent();
        PreCalculatedMovementActivity movementActivity2 = (PreCalculatedMovementActivity) moveThenAttackActivity2.getCurrentActivity();
        assertThat(movementActivity2.getTargetX(), is(13));
        assertThat(movementActivity2.getTargetY(), is(12));

        // check target for skeleton
        MoveThenAttackActivity moveThenAttackActivity3 = (MoveThenAttackActivity) skeleton.getActivityStack().getCurrent();
        PreCalculatedMovementActivity movementActivity3 = (PreCalculatedMovementActivity) moveThenAttackActivity3.getCurrentActivity();
        assertThat(movementActivity3.getTargetX(), is(13));
        assertThat(movementActivity3.getTargetY(), is(11));

        // perform next task for hero
        for(int i = 0; i <= hero.getMovementSpeed() * 3; i++) {
            hero.getActivityStack().performNext();
            Thread.sleep(5); // this is needed for pathfinder to generate the path in time
        }

        //for(int i = 0; i < hero.getAttackSpeed(); i++)
            //hero.getActivityStack().performNext();

        assertThat(hero.getX(), is(11));
        assertThat(hero.getY(), is(11));

    }

}