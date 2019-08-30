package com.mygdx.game.logic.activity.manager;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.component.skill.WeaponSkill;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.hero.Wizard;
import com.mygdx.game.actor.monster.Goblin;
import com.mygdx.game.actor.monster.Skeleton;
import com.mygdx.game.item.weapon.bow.LongBow;
import com.mygdx.game.item.weapon.sword.ShortSword;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.compound.MoveThenAttackActivity;
import com.mygdx.game.logic.activity.single.OffensiveSpellCastActivity;
import com.mygdx.game.logic.activity.single.PreCalculatedMovementActivity;
import com.mygdx.game.logic.activity.single.RangedAttackActivity;
import com.mygdx.game.logic.activity.single.SimpleAttackActivity;
import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ActivityManagerCombatTest {

    @Test
    public void testFindClosestEnemyAndMove_allMelee() throws InterruptedException {
        ActivityManager activityManager = new ActivityManager();

        VisibilityMask visibilityMask = new VisibilityMask(100, 100);
        Map2D dungeon = new DummyDungeonCreator().create(5);
        VisibilityMapRegistry.INSTANCE.add(dungeon, visibilityMask);

        Actor hero = new Warrior();
        hero.setCurrentMap(dungeon);
        hero.setCoordinates(new Point(10, 10));

        hero.equip(new ShortSword());
        Actor goblin = new Goblin();
        goblin.setCurrentMap(dungeon);
        goblin.equip(new ShortSword());
        goblin.setCoordinates(new Point(15, 15));

        Actor skeleton = new Skeleton();
        skeleton.setCurrentMap(dungeon);
        skeleton.equip(new ShortSword());
        skeleton.setCoordinates(Point.of(0, 0));

        Actor skeleton2 = new Skeleton();
        skeleton2.setCurrentMap(dungeon);
        skeleton2.setCoordinates(Point.of(5, 5));
        skeleton2.equip(new ShortSword());

        visibilityMask.setValue(15, 15, hero);
        visibilityMask.setValue(10, 10, goblin);
        visibilityMask.setValue(10, 10, skeleton);

        ActorRegistry.INSTANCE.add(dungeon, hero);
        ActorRegistry.INSTANCE.add(dungeon, goblin);
        ActorRegistry.INSTANCE.add(dungeon, skeleton);
        ActorRegistry.INSTANCE.add(dungeon, skeleton2);
        MapRegistry.INSTANCE.add(dungeon);

        activityManager.manage(hero);
        activityManager.manage(goblin);
        activityManager.manage(skeleton2);

        assertThat(hero.getActivityStack().contains(SimpleAttackActivity.class), is(true));
        assertThat(goblin.getActivityStack().contains(SimpleAttackActivity.class), is(true));
        assertThat(skeleton2.getActivityStack().contains(SimpleAttackActivity.class), is(false));

        // perform next task for goblin
        for (int i = 0; i <= goblin.getMovementSpeed() * 3; i++) {
            goblin.getActivityStack().performNext();
            Thread.sleep(5); // this is needed for pathfinder to generate the path in time
        }
        if (Config.Engine.ENABLE_8_WAYS_PATHFINDING) {
            assertThat(goblin.getX(), is(13));
            assertThat(goblin.getY(), is(13));
        } else {
            assertThat(goblin.getX(), is(14));
            assertThat(goblin.getY(), is(14));
        }

        activityManager.manage(skeleton);
        assertThat(skeleton.getActivityStack().contains(SimpleAttackActivity.class), is(true));


        // perform next task for skeleton
        for (int i = 0; i <= skeleton.getMovementSpeed() * 3; i++) {
            skeleton.getActivityStack().performNext();
            Thread.sleep(5); // this is needed for pathfinder to generate the path in time
        }
        if (Config.Engine.ENABLE_8_WAYS_PATHFINDING) {
            assertThat(skeleton.getX(), is(2));
            assertThat(skeleton.getY(), is(2));
        } else {
            assertThat(skeleton.getX(), is(2));
            assertThat(skeleton.getY(), is(0));
        }

        // check target for goblin
        MoveThenAttackActivity moveThenAttackActivity = (MoveThenAttackActivity) goblin.getActivityStack().getCurrent();
        PreCalculatedMovementActivity movementActivity = (PreCalculatedMovementActivity) moveThenAttackActivity.getCurrentActivity();
        assertThat(movementActivity.getTargetX(), is(13));
        assertThat(movementActivity.getTargetY(), is(13));

        // check target for warrior
        MoveThenAttackActivity moveThenAttackActivity2 = (MoveThenAttackActivity) hero.getActivityStack().getCurrent();
        PreCalculatedMovementActivity movementActivity2 = (PreCalculatedMovementActivity) moveThenAttackActivity2.getCurrentActivity();
        if (Config.Engine.ENABLE_8_WAYS_PATHFINDING) {
            assertThat(movementActivity2.getTargetX(), is(12));
            assertThat(movementActivity2.getTargetY(), is(12));
        } else {
            assertThat(movementActivity2.getTargetX(), is(13));
            assertThat(movementActivity2.getTargetY(), is(12));
        }

        // check target for skeleton
        MoveThenAttackActivity moveThenAttackActivity3 = (MoveThenAttackActivity) skeleton.getActivityStack().getCurrent();
        PreCalculatedMovementActivity movementActivity3 = (PreCalculatedMovementActivity) moveThenAttackActivity3.getCurrentActivity();

        if (Config.Engine.ENABLE_8_WAYS_PATHFINDING) {
            assertThat(movementActivity3.getTargetX(), is(11));
            assertThat(movementActivity3.getTargetY(), is(11));
        } else {
            assertThat(movementActivity3.getTargetX(), is(13));
            assertThat(movementActivity3.getTargetY(), is(11));
        }

        // perform next task for hero
        for (int i = 0; i <= hero.getMovementSpeed() * 3; i++) {
            hero.getActivityStack().performNext();
            Thread.sleep(5); // this is needed for pathfinder to generate the path in time
        }

        if (Config.Engine.ENABLE_8_WAYS_PATHFINDING) {
            assertThat(hero.getX(), is(12));
            assertThat(hero.getY(), is(12));
        } else {
            assertThat(hero.getX(), is(11));
            assertThat(hero.getY(), is(11));
        }

        visibilityMask.setValue(hero.getX(), hero.getY(), skeleton2);

        activityManager.manage(skeleton2);

        MoveThenAttackActivity moveThenAttackActivity4 = (MoveThenAttackActivity) skeleton2.getActivityStack().getCurrent();
        PreCalculatedMovementActivity movementActivity4 = (PreCalculatedMovementActivity) moveThenAttackActivity4.getCurrentActivity();
        if (Config.Engine.ENABLE_8_WAYS_PATHFINDING) {
            assertThat(movementActivity4.getTargetX(), is(11));
            assertThat(movementActivity4.getTargetY(), is(11));
        } else {
            assertThat(movementActivity4.getTargetX(), is(13));
            assertThat(movementActivity4.getTargetY(), is(11));
        }
    }

    @Test
    public void testFindClosestEnemyAndMove_rangedVsSpellCasterWithNoManaVsMelee() throws InterruptedException {
        ActivityManager activityManager = new ActivityManager();

        VisibilityMask visibilityMask = new VisibilityMask(100, 100);
        Map2D dungeon = new DummyDungeonCreator().create(5);
        VisibilityMapRegistry.INSTANCE.add(dungeon, visibilityMask);

        Actor hero = new Wizard();
        hero.setCurrentMap(dungeon);
        hero.setCoordinates(new Point(10, 10));
        hero.equip(new ShortSword());
        hero.setMana(0);

        Actor goblin = new Goblin();
        goblin.setCurrentMap(dungeon);
        goblin.equip(new ShortSword());
        goblin.setCoordinates(new Point(15, 15));

        Actor skeleton = new Skeleton();
        skeleton.setCurrentMap(dungeon);
        skeleton.equip(new ShortSword());
        skeleton.setCoordinates(Point.of(0, 0));

        Actor skeleton2 = new Skeleton();
        skeleton2.setCurrentMap(dungeon);
        skeleton2.setCoordinates(Point.of(5, 5));
        skeleton2.equip(new ShortSword());

        visibilityMask.setValue(15, 15, hero);
        visibilityMask.setValue(10, 10, goblin);
        visibilityMask.setValue(10, 10, skeleton);

        ActorRegistry.INSTANCE.add(dungeon, hero);
        ActorRegistry.INSTANCE.add(dungeon, goblin);
        ActorRegistry.INSTANCE.add(dungeon, skeleton);
        ActorRegistry.INSTANCE.add(dungeon, skeleton2);
        MapRegistry.INSTANCE.add(dungeon);

        activityManager.manage(hero);
        activityManager.manage(goblin);
        activityManager.manage(skeleton2);

        assertThat(hero.getActivityStack().contains(SimpleAttackActivity.class), is(true));
        assertThat(goblin.getActivityStack().contains(SimpleAttackActivity.class), is(true));
        assertThat(skeleton2.getActivityStack().contains(SimpleAttackActivity.class), is(false));

        // perform next task for goblin
        for (int i = 0; i <= goblin.getMovementSpeed() * 3; i++) {
            goblin.getActivityStack().performNext();
            Thread.sleep(5); // this is needed for pathfinder to generate the path in time
        }
        assertThat(goblin.getX(), is(14));
        assertThat(goblin.getY(), is(14));

        activityManager.manage(skeleton);
        assertThat(skeleton.getActivityStack().contains(SimpleAttackActivity.class), is(true));


        // perform next task for skeleton
        for (int i = 0; i <= skeleton.getMovementSpeed() * 3; i++) {
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
        for (int i = 0; i <= hero.getMovementSpeed() * 3; i++) {
            hero.getActivityStack().performNext();
            Thread.sleep(5); // this is needed for pathfinder to generate the path in time
        }
        assertThat(hero.getX(), is(11));
        assertThat(hero.getY(), is(11));

        visibilityMask.setValue(11, 11, skeleton2);
        activityManager.manage(skeleton2);

        MoveThenAttackActivity moveThenAttackActivity4 = (MoveThenAttackActivity) skeleton2.getActivityStack().getCurrent();
        PreCalculatedMovementActivity movementActivity4 = (PreCalculatedMovementActivity) moveThenAttackActivity4.getCurrentActivity();
        assertThat(movementActivity4.getTargetX(), is(13));
        assertThat(movementActivity4.getTargetY(), is(11));
    }

    @Test
    public void testFindClosestEnemyAndMove_rangedAttack() throws InterruptedException {
        ActivityManager activityManager = new ActivityManager();

        VisibilityMask visibilityMask = new VisibilityMask(100, 100);
        Map2D dungeon = new DummyDungeonCreator().create(5);
        VisibilityMapRegistry.INSTANCE.add(dungeon, visibilityMask);

        Actor hero = new Warrior();
        hero.setCurrentMap(dungeon);
        hero.setCoordinates(new Point(10, 10));
        hero.getWeaponSkills().put(WeaponSkill.Bow, 2);
        hero.equip(new LongBow());
        Actor goblin = new Goblin();
        goblin.setCurrentMap(dungeon);
        goblin.equip(new ShortSword());
        goblin.setCoordinates(new Point(15, 15));

        Actor skeleton = new Skeleton();
        skeleton.setCurrentMap(dungeon);
        skeleton.equip(new ShortSword());
        skeleton.setCoordinates(Point.of(0, 0));

        visibilityMask.setValue(15, 15, hero);
        visibilityMask.setValue(10, 10, goblin);
        visibilityMask.setValue(10, 10, skeleton);

        ActorRegistry.INSTANCE.add(dungeon, hero);
        ActorRegistry.INSTANCE.add(dungeon, goblin);
        ActorRegistry.INSTANCE.add(dungeon, skeleton);
        MapRegistry.INSTANCE.add(dungeon);

        activityManager.manage(hero);
        activityManager.manage(goblin);

        assertThat(hero.getActivityStack().contains(RangedAttackActivity.class), is(true));
        assertThat(goblin.getActivityStack().contains(SimpleAttackActivity.class), is(true));

        // perform next task for goblin
        for (int i = 0; i <= goblin.getMovementSpeed() * 3; i++) {
            goblin.getActivityStack().performNext();
            Thread.sleep(5); // this is needed for pathfinder to generate the path in time
        }
        assertThat(goblin.getX(), is(14));
        assertThat(goblin.getY(), is(14));

        activityManager.manage(skeleton);
        assertThat(skeleton.getActivityStack().contains(SimpleAttackActivity.class), is(true));


        // perform next task for skeleton
        for (int i = 0; i <= skeleton.getMovementSpeed() * 3; i++) {
            skeleton.getActivityStack().performNext();
            Thread.sleep(5); // this is needed for pathfinder to generate the path in time
        }
        assertThat(skeleton.getX(), is(1));
        assertThat(skeleton.getY(), is(1));

        // check target for goblin
        MoveThenAttackActivity moveThenAttackActivity = (MoveThenAttackActivity) goblin.getActivityStack().getCurrent();
        PreCalculatedMovementActivity movementActivity = (PreCalculatedMovementActivity) moveThenAttackActivity.getCurrentActivity();
        assertThat(movementActivity.getTargetX(), is(10));
        assertThat(movementActivity.getTargetY(), is(11));

        // check target for warrior
        RangedAttackActivity rangedAttackActivity = (RangedAttackActivity) hero.getActivityStack().getCurrent();
        assertThat(rangedAttackActivity, is(notNullValue()));

        // check target for skeleton
        MoveThenAttackActivity moveThenAttackActivity3 = (MoveThenAttackActivity) skeleton.getActivityStack().getCurrent();
        PreCalculatedMovementActivity movementActivity3 = (PreCalculatedMovementActivity) moveThenAttackActivity3.getCurrentActivity();
        assertThat(movementActivity3.getTargetX(), is(10));
        assertThat(movementActivity3.getTargetY(), is(9));

        assertThat(hero.getX(), is(10));
        assertThat(hero.getY(), is(10));
    }

    @Test
    public void testFindClosestEnemyAndMove_MeleeVsSpellCaster() throws InterruptedException {
        ActivityManager activityManager = new ActivityManager();

        VisibilityMask visibilityMask = new VisibilityMask(100, 100);
        Map2D dungeon = new DummyDungeonCreator().create(5);
        VisibilityMapRegistry.INSTANCE.add(dungeon, visibilityMask);

        Actor hero = new Wizard();
        hero.setCurrentMap(dungeon);
        hero.setCoordinates(new Point(10, 10));

        hero.getWeaponSkills().put(WeaponSkill.Bow, 2);
        hero.equip(new LongBow());
        hero.setHp(1000);
        hero.setMana(1000);

        Actor goblin = new Goblin();
        goblin.setCurrentMap(dungeon);
        goblin.equip(new ShortSword());
        goblin.setCoordinates(new Point(15, 15));
        goblin.setHp(1000);

        visibilityMask.setValue(15, 15, hero);
        visibilityMask.setValue(10, 10, goblin);

        ActorRegistry.INSTANCE.add(dungeon, hero);
        ActorRegistry.INSTANCE.add(dungeon, goblin);

        MapRegistry.INSTANCE.add(dungeon);

        activityManager.manage(goblin);
        activityManager.manage(hero);

        assertThat(hero.getActivityStack().contains(OffensiveSpellCastActivity.class), is(true));
        assertThat(goblin.getActivityStack().contains(SimpleAttackActivity.class), is(true));
    }

    @Test
    public void testFindClosestEnemyAndMove_rangedAttackVsMelee() throws InterruptedException {
        ActivityManager activityManager = new ActivityManager();

        VisibilityMask visibilityMask = new VisibilityMask(100, 100);
        Map2D dungeon = new DummyDungeonCreator().create(5);
        VisibilityMapRegistry.INSTANCE.add(dungeon, visibilityMask);

        Actor hero = new Warrior();
        hero.setCurrentMap(dungeon);
        hero.setCoordinates(new Point(10, 10));
        hero.getWeaponSkills().put(WeaponSkill.Bow, 2);
        hero.equip(new LongBow());
        hero.setHp(1000);

        Actor goblin = new Goblin();
        goblin.setCurrentMap(dungeon);
        goblin.equip(new ShortSword());
        goblin.setCoordinates(new Point(15, 15));
        goblin.setHp(1000);


        visibilityMask.setValue(15, 15, hero);
        visibilityMask.setValue(10, 10, goblin);

        ActorRegistry.INSTANCE.add(dungeon, hero);
        ActorRegistry.INSTANCE.add(dungeon, goblin);

        MapRegistry.INSTANCE.add(dungeon);

        activityManager.manage(goblin);
        activityManager.manage(hero);


        assertThat(hero.getActivityStack().contains(RangedAttackActivity.class), is(true));
        assertThat(goblin.getActivityStack().contains(SimpleAttackActivity.class), is(true));
    }

    @Test
    public void testFindClosestEnemyAndMove_rangedAttackVsRangedAttack() throws InterruptedException {
        ActivityManager activityManager = new ActivityManager();

        VisibilityMask visibilityMask = new VisibilityMask(100, 100);
        Map2D dungeon = new DummyDungeonCreator().create(5);
        VisibilityMapRegistry.INSTANCE.add(dungeon, visibilityMask);

        Actor hero = new Warrior();
        hero.setCurrentMap(dungeon);
        hero.setCoordinates(new Point(10, 10));
        hero.getWeaponSkills().put(WeaponSkill.Bow, 2);
        hero.equip(new LongBow());
        hero.setHp(1000);

        Actor goblin = new Goblin();
        goblin.setCurrentMap(dungeon);
        goblin.equip(new LongBow());
        goblin.getWeaponSkills().put(WeaponSkill.Bow, 2);
        goblin.setCoordinates(new Point(15, 15));
        goblin.setHp(1000);


        visibilityMask.setValue(15, 15, hero);
        visibilityMask.setValue(10, 10, goblin);

        ActorRegistry.INSTANCE.add(dungeon, hero);
        ActorRegistry.INSTANCE.add(dungeon, goblin);

        MapRegistry.INSTANCE.add(dungeon);

        activityManager.manage(hero);
        activityManager.manage(goblin);

        assertThat(hero.getActivityStack().contains(RangedAttackActivity.class), is(true));
        assertThat(goblin.getActivityStack().contains(RangedAttackActivity.class), is(true));

        // perform next task for goblin
        for (int i = 0; i <= goblin.getMovementSpeed() * 3; i++) {
            goblin.getActivityStack().performNext();
            Thread.sleep(5); // this is needed for pathfinder to generate the path in time
        }
        assertThat(goblin.getX(), is(15));
        assertThat(goblin.getY(), is(15));

        // check target for goblin
        RangedAttackActivity rangedAttackActivity = (RangedAttackActivity) goblin.getActivityStack().getCurrent();

        assertThat(rangedAttackActivity.getEnemy(), is(hero));

        // check target for warrior
        RangedAttackActivity rangedAttackActivity2 = (RangedAttackActivity) hero.getActivityStack().getCurrent();
        assertThat(rangedAttackActivity2.getEnemy(), is(goblin));

        assertThat(hero.getX(), is(10));
        assertThat(hero.getY(), is(10));
    }

    @Test
    public void testFindClosestEnemyAndMove_rangedAttackVsSpellCasterVsMelee() throws InterruptedException {
        ActivityManager activityManager = new ActivityManager();

        VisibilityMask visibilityMask = new VisibilityMask(100, 100);
        Map2D dungeon = new DummyDungeonCreator().create(5);
        VisibilityMapRegistry.INSTANCE.add(dungeon, visibilityMask);

        Actor hero = new Wizard();
        hero.setCurrentMap(dungeon);
        hero.setCoordinates(new Point(10, 10));
        hero.getWeaponSkills().put(WeaponSkill.Bow, 2);
        hero.equip(new LongBow());
        hero.setMana(100);
        hero.setHp(100);

        Actor goblin = new Goblin();
        goblin.setCurrentMap(dungeon);
        goblin.equip(new LongBow());
        goblin.setCoordinates(new Point(15, 15));

        Actor skeleton = new Skeleton();
        skeleton.setCurrentMap(dungeon);
        skeleton.equip(new ShortSword());
        skeleton.setCoordinates(Point.of(0, 0));

        visibilityMask.setValue(15, 15, hero);
        visibilityMask.setValue(10, 10, goblin);
        visibilityMask.setValue(10, 10, skeleton);

        ActorRegistry.INSTANCE.add(dungeon, hero);
        ActorRegistry.INSTANCE.add(dungeon, goblin);
        ActorRegistry.INSTANCE.add(dungeon, skeleton);
        MapRegistry.INSTANCE.add(dungeon);

        activityManager.manage(hero);
        activityManager.manage(goblin);

        assertThat(hero.getActivityStack().contains(OffensiveSpellCastActivity.class), is(true));
        assertThat(goblin.getActivityStack().contains(RangedAttackActivity.class), is(true));

        // perform next task for goblin
        for (int i = 0; i <= goblin.getAttackSpeed() * 3; i++) {
            goblin.getActivityStack().performNext();
            Thread.sleep(5); // this is needed for pathfinder to generate the path in time
        }
        assertThat(goblin.getX(), is(15));
        assertThat(goblin.getY(), is(15));

        activityManager.manage(skeleton);
        assertThat(skeleton.getActivityStack().contains(SimpleAttackActivity.class), is(true));


        // perform next task for skeleton
        for (int i = 0; i <= skeleton.getMovementSpeed() * 3; i++) {
            skeleton.getActivityStack().performNext();
            Thread.sleep(5); // this is needed for pathfinder to generate the path in time
        }
        assertThat(skeleton.getX(), is(1));
        assertThat(skeleton.getY(), is(1));

        // check target for goblin
        RangedAttackActivity rangedAttackActivity = (RangedAttackActivity) goblin.getActivityStack().getCurrent();

        assertThat(rangedAttackActivity.getEnemy(), is(hero));

        // check target for warrior
        OffensiveSpellCastActivity offensiveSpellCastActivity = (OffensiveSpellCastActivity) hero.getActivityStack().getCurrent();
        assertThat(offensiveSpellCastActivity.getTarget(), is(goblin));

        // check target for skeleton
        MoveThenAttackActivity moveThenAttackActivity3 = (MoveThenAttackActivity) skeleton.getActivityStack().getCurrent();
        PreCalculatedMovementActivity movementActivity3 = (PreCalculatedMovementActivity) moveThenAttackActivity3.getCurrentActivity();
        assertThat(movementActivity3.getTargetX(), is(10));
        assertThat(movementActivity3.getTargetY(), is(9));

        assertThat(hero.getX(), is(10));
        assertThat(hero.getY(), is(10));
    }


}