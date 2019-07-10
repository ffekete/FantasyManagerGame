package com.mygdx.game.logic;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Wizard;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.object.interactive.DungeonEntrance;
import com.mygdx.game.object.interactive.Ladder;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.ObjectRegistry;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CharacterMap2dSwitcherTest {

    @Test
    public void switchTo() {
        Map2D  from = new DummyDungeonCreator().create(3);
        Map2D  to = new DummyDungeonCreator().create(3);

        Actor wizard = new Wizard();
        wizard.setCurrentMap(from);

        Ladder ladder = new Ladder(from, to);
        ladder.setCoordinates(Point.of(8,8));

        ActorRegistry.INSTANCE.add(from, wizard);
        ObjectRegistry.INSTANCE.add(to, Cluster.of(5,5), new DungeonEntrance(to, from));
        ObjectRegistry.INSTANCE.add(to, Cluster.of(8,8), ladder);

        CharacterMap2dSwitcher characterMap2dSwitcher = CharacterMap2dSwitcher.INSTANCE;

        assertThat(wizard.getCurrentMap(), is(from));

        characterMap2dSwitcher.switchTo(to, from, wizard);

        assertThat(wizard.getCurrentMap(), is(to));
        assertThat(wizard.getCoordinates(), is(Point.of(9,8)));
    }


}