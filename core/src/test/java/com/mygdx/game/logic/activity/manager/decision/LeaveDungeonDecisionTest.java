package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.monster.Skeleton;
import com.mygdx.game.logic.activity.compound.MoveAndInteractActivity;
import com.mygdx.game.map.dungeon.Dungeon;
import com.mygdx.game.map.worldmap.WorldMap;
import com.mygdx.game.map.worldmap.WorldMapGenerator;
import com.mygdx.game.object.LinkedWorldObjectFactory;
import com.mygdx.game.object.interactive.DungeonEntrance;
import com.mygdx.game.object.placement.ObjectPlacement;

import com.mygdx.game.registry.ObjectRegistry;
import org.junit.Before;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LeaveDungeonDecisionTest {

    @Before
    public void setUp() {
        ObjectRegistry.INSTANCE.clear();
    }

    @Test
    public void shouldFail_heroIsOnWorldMap() {

        Actor actor = new Warrior();
        WorldMap worldMap = (WorldMap) new WorldMapGenerator().create(3);
        actor.setCurrentMap(worldMap);

        Decision leaveDungeonDecision = new LeaveDungeonDecision();

        boolean result = leaveDungeonDecision.decide(actor);
        assertThat(result, is(false));
    }

    @Test
    public void shouldFail_dungeonIsNotExplored() {

        Actor actor = new Warrior();
        Dungeon map = mock(Dungeon.class);
        when(map.areAllLevelsExplored()).thenReturn(false);

        actor.setCurrentMap(map);

        Decision leaveDungeonDecision = new LeaveDungeonDecision();

        boolean result = leaveDungeonDecision.decide(actor);
        assertThat(result, is(false));
    }

    @Test
    public void shouldReturnTrue_actorAlreadyLeaving() {

        Actor actor = new Warrior();
        WorldMap worldMap = new WorldMap(1, 1);
        Dungeon dungeon = mock(Dungeon.class);
        when(dungeon.areAllLevelsExplored()).thenReturn(true);
        DungeonEntrance dungeonEntrance = (DungeonEntrance) LinkedWorldObjectFactory.INSTANCE.create(DungeonEntrance.class, worldMap, dungeon, ObjectPlacement.FIXED, ObjectPlacement.FIXED);

        actor.setCurrentMap(dungeon);
        actor.getActivityStack().add(new MoveAndInteractActivity(1));

        Decision leaveDungeonDecision = new LeaveDungeonDecision();

        boolean result = leaveDungeonDecision.decide(actor);
        assertThat(result, is(true));
    }

    @Test
    public void shouldPass() {
        Actor actor = new Warrior();
        WorldMap worldMap = new WorldMap(1, 1);
        Dungeon dungeon = mock(Dungeon.class);
        when(dungeon.areAllLevelsExplored()).thenReturn(true);
        DungeonEntrance dungeonEntrance = (DungeonEntrance) LinkedWorldObjectFactory.INSTANCE.create(DungeonEntrance.class, worldMap, dungeon, ObjectPlacement.FIXED, ObjectPlacement.FIXED);

        actor.setCurrentMap(dungeon);

        Decision leaveDungeonDecision = new LeaveDungeonDecision();

        boolean result = leaveDungeonDecision.decide(actor);
        assertThat(result, is(true));
    }

    @Test
    public void shouldFail_actorCannotLeave() {
        Actor actor = new Skeleton();
        WorldMap worldMap = new WorldMap(1, 1);
        Dungeon dungeon = mock(Dungeon.class);
        when(dungeon.areAllLevelsExplored()).thenReturn(true);
        DungeonEntrance dungeonEntrance = (DungeonEntrance) LinkedWorldObjectFactory.INSTANCE.create(DungeonEntrance.class, worldMap, dungeon, ObjectPlacement.FIXED, ObjectPlacement.FIXED);

        actor.setCurrentMap(dungeon);

        Decision leaveDungeonDecision = new LeaveDungeonDecision();

        boolean result = leaveDungeonDecision.decide(actor);
        assertThat(result, is(false));
    }

    @Test
    public void shouldFail_actorIsOnWorldMap() {
        Actor actor = new Warrior();
        WorldMap worldMap = new WorldMap(1, 1);
        Dungeon dungeon = mock(Dungeon.class);
        when(dungeon.areAllLevelsExplored()).thenReturn(true);
        DungeonEntrance dungeonEntrance = (DungeonEntrance) LinkedWorldObjectFactory.INSTANCE.create(DungeonEntrance.class, worldMap, dungeon, ObjectPlacement.FIXED, ObjectPlacement.FIXED);

        actor.setCurrentMap(worldMap);

        Decision leaveDungeonDecision = new LeaveDungeonDecision();

        boolean result = leaveDungeonDecision.decide(actor);
        assertThat(result, is(false));
    }

}