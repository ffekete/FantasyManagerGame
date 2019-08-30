package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.monster.Skeleton;
import com.mygdx.game.item.weapon.bow.LongBow;
import com.mygdx.game.item.weapon.sword.ShortSword;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.single.PreCalculatedMovementActivity;
import com.mygdx.game.logic.visibility.VisitedArea;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MoveAndAttackDecisionTest {

    private final ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    private final MapRegistry mapRegistry = MapRegistry.INSTANCE;

    @Test
    public void decideTest() {
        // given
        Map2D dungeon = new DummyDungeonCreator().create(5);
        VisitedArea[][] map = new VisitedArea[Config.Dungeon.DUNGEON_WIDTH][Config.Dungeon.DUNGEON_HEIGHT];

        for (int i = 0; i < Config.Dungeon.DUNGEON_WIDTH; i++) {
            for (int j = 0; j < Config.Dungeon.DUNGEON_HEIGHT; j++) {
                map[i][j] = VisitedArea.NOT_VISITED;
            }
        }

        map[5][5] = VisitedArea.VISIBLE;
        map[1][1] = VisitedArea.VISIBLE;

        dungeon.setVisitedAreaMap(map);

        mapRegistry.setCurrentMapToShow(dungeon);
        mapRegistry.add(dungeon);

        Actor hero = new Warrior();
        hero.setCurrentMap(dungeon);
        hero.setCoordinates(Point.of(1,1));
        hero.setRightHandItem(new ShortSword());

        Actor skeleton = new Skeleton();
        skeleton.setCurrentMap(dungeon);
        skeleton.setCoordinates(Point.of(0,5));
        skeleton.setRightHandItem(new ShortSword());

        Actor skeleton2 = new Skeleton();
        skeleton2.setCurrentMap(dungeon);
        skeleton2.setCoordinates(Point.of(5,0));
        skeleton2.setRightHandItem(new ShortSword());

        MoveAndAttackDecision moveAndAttackDecision = new MoveAndAttackDecision();

        actorRegistry.add(dungeon, hero);
        actorRegistry.add(dungeon, skeleton);
        actorRegistry.add(dungeon, skeleton2);

        dungeon.getVisitedareaMap()[1][1] = VisitedArea.VISIBLE;
        dungeon.getVisitedareaMap()[0][5] = VisitedArea.VISIBLE;
        dungeon.getVisitedareaMap()[5][0] = VisitedArea.VISIBLE;

        VisibilityMapRegistry.INSTANCE.getFor(dungeon).setValue(0,5, hero);
        VisibilityMapRegistry.INSTANCE.getFor(dungeon).setValue(5,0, hero);
        VisibilityMapRegistry.INSTANCE.getFor(dungeon).setValue(1,1, skeleton);
        VisibilityMapRegistry.INSTANCE.getFor(dungeon).setValue(1,1, skeleton2);

        // when

        boolean decisionResult = moveAndAttackDecision.decide(hero);
        boolean decisionResultForSkeleton = moveAndAttackDecision.decide(skeleton);
        boolean decisionResultForSkeleton2 = moveAndAttackDecision.decide(skeleton2);

        // then
        assertThat(decisionResult, is(true));
        assertThat(decisionResultForSkeleton, is(true));
        assertThat(decisionResultForSkeleton2, is(true));

        assertThat(hero.getActivityStack().getSize(), is(2));
        assertThat(skeleton.getActivityStack().getSize(), is(2));
        assertThat(skeleton2.getActivityStack().getSize(), is(2));
        Activity heroActivity = hero.getActivityStack().getCurrent();
        assertThat(PreCalculatedMovementActivity.class.isAssignableFrom(heroActivity.getCurrentClass()), is(true));
        Activity skeletonActivity = skeleton.getActivityStack().getCurrent();
        assertThat(PreCalculatedMovementActivity.class.isAssignableFrom(skeletonActivity.getCurrentClass()), is(true));
        Activity skeletonActivity2 = skeleton2.getActivityStack().getCurrent();
        assertThat(PreCalculatedMovementActivity.class.isAssignableFrom(skeletonActivity2.getCurrentClass()), is(true));

        // then run again
        decisionResult = moveAndAttackDecision.decide(hero);
        decisionResultForSkeleton = moveAndAttackDecision.decide(skeleton);
        decisionResultForSkeleton2 = moveAndAttackDecision.decide(skeleton2);

        // then
        assertThat(decisionResult, is(true));
        assertThat(decisionResultForSkeleton, is(true));
        assertThat(decisionResultForSkeleton2, is(true));

        assertThat(hero.getActivityStack().getSize(), is(2));
        assertThat(skeleton.getActivityStack().getSize(), is(2));
        assertThat(skeleton2.getActivityStack().getSize(), is(2));
        heroActivity = hero.getActivityStack().getCurrent();
        assertThat(PreCalculatedMovementActivity.class.isAssignableFrom(heroActivity.getCurrentClass()), is(true));
        skeletonActivity = skeleton.getActivityStack().getCurrent();
        assertThat(PreCalculatedMovementActivity.class.isAssignableFrom(skeletonActivity.getCurrentClass()), is(true));
        skeletonActivity2 = skeleton2.getActivityStack().getCurrent();
        assertThat(PreCalculatedMovementActivity.class.isAssignableFrom(skeletonActivity2.getCurrentClass()), is(true));
    }

    @Test
    public void decideTest_shouldFail_rangedEquipped() {
        // given
        Map2D dungeon = new DummyDungeonCreator().create(5);
        VisitedArea[][] map = new VisitedArea[Config.Dungeon.DUNGEON_WIDTH][Config.Dungeon.DUNGEON_HEIGHT];

        for (int i = 0; i < Config.Dungeon.DUNGEON_WIDTH; i++) {
            for (int j = 0; j < Config.Dungeon.DUNGEON_HEIGHT; j++) {
                map[i][j] = VisitedArea.NOT_VISITED;
            }
        }

        map[5][5] = VisitedArea.VISIBLE;
        map[1][1] = VisitedArea.VISIBLE;

        dungeon.setVisitedAreaMap(map);

        mapRegistry.setCurrentMapToShow(dungeon);
        mapRegistry.add(dungeon);

        Actor hero = new Warrior();
        hero.setCurrentMap(dungeon);
        hero.setCoordinates(Point.of(1,1));
        hero.setRightHandItem(new LongBow());

        Actor skeleton = new Skeleton();
        skeleton.setCurrentMap(dungeon);
        skeleton.setCoordinates(Point.of(0,5));
        skeleton.setRightHandItem(new ShortSword());

        Actor skeleton2 = new Skeleton();
        skeleton2.setCurrentMap(dungeon);
        skeleton2.setCoordinates(Point.of(5,0));
        skeleton2.setRightHandItem(new ShortSword());

        MoveAndAttackDecision moveAndAttackDecision = new MoveAndAttackDecision();

        actorRegistry.add(dungeon, hero);
        actorRegistry.add(dungeon, skeleton);
        actorRegistry.add(dungeon, skeleton2);

        dungeon.getVisitedareaMap()[1][1] = VisitedArea.VISIBLE;
        dungeon.getVisitedareaMap()[0][5] = VisitedArea.VISIBLE;
        dungeon.getVisitedareaMap()[5][0] = VisitedArea.VISIBLE;

        VisibilityMapRegistry.INSTANCE.getFor(dungeon).setValue(0,5, hero);
        VisibilityMapRegistry.INSTANCE.getFor(dungeon).setValue(5,0, hero);
        VisibilityMapRegistry.INSTANCE.getFor(dungeon).setValue(1,1, skeleton);
        VisibilityMapRegistry.INSTANCE.getFor(dungeon).setValue(1,1, skeleton2);

        // when

        boolean decisionResult = moveAndAttackDecision.decide(hero);

        // then
        assertThat(decisionResult, is(false));
    }


}