package com.mygdx.game.logic.activity.single;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.component.attribute.Attributes;
import com.mygdx.game.actor.hero.Wizard;
import com.mygdx.game.actor.monster.Goblin;
import com.mygdx.game.effect.MovementSpeedReduction;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.registry.EffectRegistry;
import com.mygdx.game.registry.MapRegistry;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.spy;

public class MovementActivityTest {

    @Test
    public void testForSlowedActor() {

        Map2D dungeon = new DummyDungeonCreator().create(3);

        Actor actor = new Wizard();
        Actor goblin = new Goblin();

        actor.setCurrentMap(dungeon);
        goblin.setCurrentMap(dungeon);

        MapRegistry.INSTANCE.add(dungeon);

        actor.setCoordinates(Point.of(0,0));
        goblin.setCoordinates(Point.of(10,0));

        goblin.setAttribute(Attributes.Reflexes, 10);
        goblin.setAttribute(Attributes.Dexterity, 10);

        MovementActivity movementActivity = spy(new MovementActivity(goblin, actor.getX(), actor.getY(), 1, new PathFinder()));

        movementActivity.init();

        assertThat(movementActivity.getSpeed(), is(30));

        for(int i = 0; i < movementActivity.getSpeed() -1; i++) {
            movementActivity.countDown();
        }

        assertThat(movementActivity.getCounter(), is(29));

        assertThat(movementActivity.getSpeed(), is(30));

        movementActivity.update();

        assertThat(movementActivity.getSpeed(), is(30));



        for(int i = 0; i < 20; i++) {
            movementActivity.countDown();
        }

        assertThat(movementActivity.getCounter(), is(19));
        assertThat(movementActivity.getSpeed(), is(30));

        EffectRegistry.INSTANCE.add(new MovementSpeedReduction(-20), goblin);

        for(int i = 0; i < 29; i++) {
            movementActivity.countDown();
        }

        assertThat(movementActivity.getSpeed(), is(30));

        movementActivity.update();

        assertThat(movementActivity.getSpeed(), is(50));
    }

}