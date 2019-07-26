package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Ranger;
import com.mygdx.game.actor.hero.Wizard;
import com.mygdx.game.actor.monster.SkeletonWarrior;
import com.mygdx.game.item.weapon.bow.LongBow;
import com.mygdx.game.item.weapon.sword.ShortSword;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.compound.MoveThenAttackActivity;
import com.mygdx.game.logic.activity.manager.ActivityManager;
import com.mygdx.game.logic.activity.single.ExplorationActivity;
import com.mygdx.game.logic.activity.single.SupportActivity;
import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.map.dungeon.Dungeon;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SupportDecisionTest {

    @Test
    public void testShouldSupport() {
        Dungeon dungeon = new DummyDungeonCreator().create(3);
        Actor target = new Wizard();
        Actor support = new Ranger();

        target.setCurrentMap(dungeon);
        support.setCurrentMap(dungeon);

        target.equip(new ShortSword());
        support.equip(new LongBow());

        target.setCoordinates(Point.of(1, 1));
        support.setCoordinates(Point.of(5, 1));

        ActorRegistry.INSTANCE.add(dungeon, target);
        ActorRegistry.INSTANCE.add(dungeon, support);

        ActivityManager activityManager = new ActivityManager();

        activityManager.manage(target);
        activityManager.manage(support);

        assertThat(target.getActivityStack().contains(ExplorationActivity.class), is(true));
        assertThat(support.getActivityStack().contains(SupportActivity.class), is(true));
    }

    @Test
    public void testShouldNotSupportEnemy() {
        Dungeon dungeon = new DummyDungeonCreator().create(3);
        Actor target = new SkeletonWarrior();
        Actor support = new Ranger();

        VisibilityMask visibilityMask = new VisibilityMask(100, 100);
        VisibilityMapRegistry.INSTANCE.add(dungeon, visibilityMask);

        // support cannot see target
        //visibilityMask.setValue(1,1, support);
        visibilityMask.setValue(5,1, target);

        MapRegistry.INSTANCE.add(dungeon);

        target.setCurrentMap(dungeon);
        support.setCurrentMap(dungeon);

        target.equip(new ShortSword());
        support.equip(new LongBow());

        target.setCoordinates(Point.of(1, 1));
        support.setCoordinates(Point.of(5, 1));

        ActorRegistry.INSTANCE.add(dungeon, target);
        ActorRegistry.INSTANCE.add(dungeon, support);

        ActivityManager activityManager = new ActivityManager();

        activityManager.manage(target);
        activityManager.manage(support);

        assertThat(target.getActivityStack().contains(MoveThenAttackActivity.class), is(true));
        assertThat(support.getActivityStack().contains(ExplorationActivity.class), is(true));
    }

    @Test
    public void testShouldSupport_canCancel_tooFarAway() {
        Dungeon dungeon = new DummyDungeonCreator().create(3);
        Actor target = new Wizard();
        Actor support = new Ranger();

        MapRegistry.INSTANCE.add(dungeon);

        target.setCurrentMap(dungeon);
        support.setCurrentMap(dungeon);

        target.equip(new ShortSword());
        support.equip(new LongBow());

        target.setCoordinates(Point.of(1, 1));
        support.setCoordinates(Point.of(5, 1));

        ActorRegistry.INSTANCE.add(dungeon, target);
        ActorRegistry.INSTANCE.add(dungeon, support);

        ActivityManager activityManager = new ActivityManager();

        activityManager.manage(target);
        activityManager.manage(support);

        // move target far away now
        target.setCoordinates(Point.of(15, 25));

        // perform next(), realise target is too far away
        support.getActivityStack().performNext();
        assertThat(support.getActivityStack().contains(SupportActivity.class), is(false));

        activityManager.manage(target);
        activityManager.manage(support);

        assertThat(support.getActivityStack().contains(ExplorationActivity.class), is(true));
    }

    @Test
    public void testShouldSupport_canCancel_targetMovedToDungeon() {
        Dungeon dungeon = new DummyDungeonCreator().create(3);
        Dungeon dungeon2 = new DummyDungeonCreator().create(3);
        Actor target = new Wizard();
        Actor support = new Ranger();

        MapRegistry.INSTANCE.add(dungeon);
        MapRegistry.INSTANCE.add(dungeon2);

        target.setCurrentMap(dungeon);
        support.setCurrentMap(dungeon);

        target.equip(new ShortSword());
        support.equip(new LongBow());

        target.setCoordinates(Point.of(1, 1));
        support.setCoordinates(Point.of(5, 1));

        ActorRegistry.INSTANCE.add(dungeon, target);
        ActorRegistry.INSTANCE.add(dungeon, support);

        ActivityManager activityManager = new ActivityManager();

        activityManager.manage(target);
        activityManager.manage(support);

        // move target far away now
        target.setCurrentMap(dungeon2);

        // perform next(), realise target is too far away
        support.getActivityStack().performNext();
        assertThat(support.getActivityStack().contains(SupportActivity.class), is(false));

        activityManager.manage(target);
        activityManager.manage(support);

        assertThat(support.getActivityStack().contains(ExplorationActivity.class), is(true));
    }

    @Test
    public void testShouldNotSupport_actorIsAlone() {
        Dungeon dungeon = new DummyDungeonCreator().create(3);

        Actor support = new Ranger();

        MapRegistry.INSTANCE.add(dungeon);

        support.setCurrentMap(dungeon);

        support.equip(new LongBow());

        support.setCoordinates(Point.of(5, 1));

        ActorRegistry.INSTANCE.add(dungeon, support);

        ActivityManager activityManager = new ActivityManager();

        activityManager.manage(support);

        assertThat(support.getActivityStack().contains(ExplorationActivity.class), is(true));
    }

}