package com.mygdx.game.registry;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.factory.ActorFactory;
import com.mygdx.game.actor.factory.Placement;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.hero.Wizard;
import com.mygdx.game.actor.monster.Goblin;
import com.mygdx.game.actor.monster.Skeleton;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.Dungeon;
import com.mygdx.game.map.dungeon.DungeonType;
import com.mygdx.game.map.worldmap.WorldMap;
import com.mygdx.game.map.worldmap.WorldMapGenerator;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ActorRegistryTest {

    private ActorRegistry actorRegistry = new ActorRegistry();

    @Test
    public void fillTest() {
        Map2D dungeon = new Dungeon(5, 5, DungeonType.CAVE);
        Actor warrior = new Warrior();
        Actor goblin = new Goblin();
        Actor skeleton = new Skeleton();
        actorRegistry.add(dungeon, goblin);
        assertThat(actorRegistry.getAllActors().size(), is(1));
        actorRegistry.add(dungeon, warrior);
        assertThat(actorRegistry.getAllActors().size(), is(2));
        actorRegistry.add(dungeon, skeleton);
        assertThat(actorRegistry.getAllActors().size(), is(3));

        assertThat(actorRegistry.getActors(dungeon).toArray()[0], is(goblin));
        assertThat(actorRegistry.getActors(dungeon).toArray()[1], is(warrior));
        assertThat(actorRegistry.getActors(dungeon).toArray()[2], is(skeleton));
        assertThat(actorRegistry.getActors(dungeon).contains(warrior), is(true));

        actorRegistry.remove(dungeon, warrior, false);
        assertThat(actorRegistry.getActors(dungeon).contains(warrior), is(false));
    }


    @Test
    public void fillBucket() {

        ActorRegistry.INSTANCE.clear();

        WorldMap worldMap = new WorldMapGenerator().create(3);
        for (int i = 0; i < Config.Engine.BUCKET_SIZE; i++) {
            ActorFactory.INSTANCE.create(Wizard.class, worldMap, Placement.FIXED.X(1).Y(1));
            assertThat(ActorRegistry.INSTANCE.getBucketedActors(worldMap, i).size(), is(1));
        }

        for (int i = 0; i < Config.Engine.BUCKET_SIZE; i++) {
            ActorFactory.INSTANCE.create(Wizard.class, worldMap, Placement.FIXED.X(1).Y(1));
            assertThat(ActorRegistry.INSTANCE.getBucketedActors(worldMap, i).size(), is(2));
        }
    }

}