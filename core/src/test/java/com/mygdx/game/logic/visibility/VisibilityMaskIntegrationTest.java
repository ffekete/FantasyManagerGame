package com.mygdx.game.logic.visibility;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.monster.Goblin;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.creator.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.logic.Point;

import org.testng.annotations.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class VisibilityMaskIntegrationTest {

    private VisibilityCalculator visibilityCalculator = new VisibilityCalculator(Config.Dungeon.DUNGEON_WIDTH, Config.Dungeon.DUNGEON_HEIGHT);

    @Test
    public void testVisibility() {
        Map2D dungeon = new DummyDungeonCreator().create(5);
        Actor warrior = new Warrior();
        warrior.setCurrentMap(dungeon);
        warrior.setCoordinates(new Point(0,0));

        Actor goblin = new Goblin();
        goblin.setCoordinates(new Point(0, 1));
        goblin.setCurrentMap(dungeon);

        VisibilityMask mask = visibilityCalculator.generateMask(dungeon, 3, Arrays.asList(warrior, goblin));

        // row 1
        assertThat(mask.getValue(0,0).size(), is(2));
        assertThat(mask.getValue(0,1).size(), is(2));
        assertThat(mask.getValue(0,2).size(), is(2));
        assertThat(mask.getValue(0,3).size(), is(2));
        assertThat(mask.getValue(0,4).size(), is(1));

        // row 2
        assertThat(mask.getValue(1,0).size(), is(2));
        assertThat(mask.getValue(1,1).size(), is(2));
        assertThat(mask.getValue(1,2).size(), is(2));
        assertThat(mask.getValue(1,3).size(), is(2));
        assertThat(mask.getValue(1,4).size(), is(1));

        // row 3
        assertThat(mask.getValue(2,0).size(), is(2));
        assertThat(mask.getValue(2,1).size(), is(2));
        assertThat(mask.getValue(2,2).size(), is(2));
        assertThat(mask.getValue(2,3).size(), is(1));
        assertThat(mask.getValue(2,4).size(), is(0));

        // row 4
        assertThat(mask.getValue(3,0).size(), is(2));
        assertThat(mask.getValue(3,1).size(), is(2));
        assertThat(mask.getValue(3,2).size(), is(1));
        assertThat(mask.getValue(3,3).size(), is(0));

        // row 5
        assertThat(mask.getValue(4,0).size(), is(0));
        assertThat(mask.getValue(4,1).size(), is(0));
        assertThat(mask.getValue(4,2).size(), is(0));
        assertThat(mask.getValue(4,3).size(), is(0));
    }

}