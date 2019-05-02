package com.mygdx.game.registry;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.monster.Goblin;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.creator.map.dungeon.Dungeon;

import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ActorRegistryTest {

    private ActorRegistry actorRegistry = new ActorRegistry();

    @Test
    public void fillTest() {
        Map2D dungeon = new Dungeon(5,5);
        Actor warrior = new Warrior();
        actorRegistry.add(dungeon, new Goblin());
        actorRegistry.add(dungeon, warrior);
        actorRegistry.add(dungeon, new Goblin());

        assertThat(actorRegistry.getActors(dungeon).toArray()[0], is(warrior));
        assertThat(actorRegistry.getActors(dungeon).contains(warrior), is(true));

        actorRegistry.getActors(dungeon).remove(warrior);
        assertThat(actorRegistry.getActors(dungeon).contains(warrior), is(false));
    }

}