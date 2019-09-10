package com.mygdx.game.logic.visibility;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.monster.Goblin;
import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.DummyDungeonCreator;
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
        goblin.setCurrentMap(dungeon);
        goblin.setCoordinates(new Point(0, 1));

        VisibilityMask mask = visibilityCalculator.generateMask(dungeon, Arrays.asList(warrior, goblin));

        for (int i = 0; i < dungeon.getWidth(); i++) {
            for (int j = 0; j < dungeon.getHeight(); j++) {
                System.out.print(mask.getValue(i,j).size());
            }
            System.out.println();
        }

        // row 1
        assertThat(mask.getValue(0,0).size(), is(2));
        assertThat(mask.getValue(0,1).size(), is(2));
        assertThat(mask.getValue(0,2).size(), is(2));
        assertThat(mask.getValue(0,3).size(), is(2));
        assertThat(mask.getValue(0,4).size(), is(2));
        assertThat(mask.getValue(0,5).size(), is(2));
        assertThat(mask.getValue(0,6).size(), is(2));
        assertThat(mask.getValue(0,7).size(), is(2));

        // row 2
        assertThat(mask.getValue(1,0).size(), is(2));
        assertThat(mask.getValue(1,1).size(), is(2));
        assertThat(mask.getValue(1,2).size(), is(2));
        assertThat(mask.getValue(1,3).size(), is(2));
        assertThat(mask.getValue(1,4).size(), is(2));
        assertThat(mask.getValue(1,5).size(), is(2));
        assertThat(mask.getValue(1,6).size(), is(2));
        assertThat(mask.getValue(1,7).size(), is(2));

        // row 3
        assertThat(mask.getValue(2,0).size(), is(2));
        assertThat(mask.getValue(2,1).size(), is(2));
        assertThat(mask.getValue(2,2).size(), is(2));
        assertThat(mask.getValue(2,3).size(), is(2));
        assertThat(mask.getValue(2,4).size(), is(2));
        assertThat(mask.getValue(2,5).size(), is(2));
        assertThat(mask.getValue(2,6).size(), is(2));
        assertThat(mask.getValue(2,7).size(), is(2));

        // row 4
        assertThat(mask.getValue(3,0).size(), is(2));
        assertThat(mask.getValue(3,1).size(), is(2));
        assertThat(mask.getValue(3,2).size(), is(2));
        assertThat(mask.getValue(3,3).size(), is(2));
        assertThat(mask.getValue(3,4).size(), is(2));
        assertThat(mask.getValue(3,5).size(), is(2));
        assertThat(mask.getValue(3,6).size(), is(2));
        assertThat(mask.getValue(3,7).size(), is(1));

        // row 5
        assertThat(mask.getValue(4,0).size(), is(2));
        assertThat(mask.getValue(4,1).size(), is(2));
        assertThat(mask.getValue(4,2).size(), is(2));
        assertThat(mask.getValue(4,3).size(), is(2));
        assertThat(mask.getValue(4,4).size(), is(2));
        assertThat(mask.getValue(4,5).size(), is(2));
        assertThat(mask.getValue(4,6).size(), is(2));
        assertThat(mask.getValue(4,7).size(), is(1));
    }

}