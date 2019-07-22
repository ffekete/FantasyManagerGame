package com.mygdx.game.effect;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Wizard;
import com.mygdx.game.effect.manager.EffectManager;
import com.mygdx.game.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.map.dungeon.Dungeon;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.EffectRegistry;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MovementSpeedReductionOnActorTest {

    @Test
    public void test() {

        Actor actor = new Wizard();

        Dungeon dungeon = (Dungeon) new DummyDungeonCreator().create(3);

        actor.setCurrentMap(dungeon);

        ActorRegistry.INSTANCE.add(dungeon, actor);

        assertThat(actor.getAttackSpeed(), is(50));
        assertThat(actor.getMovementSpeed(), is(40));

        EffectRegistry.INSTANCE.add(new AttackSpeedReduction(-30), actor);

        EffectManager.INSTANCE.update();

        assertThat(actor.getAttackSpeed(), is(80));
        assertThat(actor.getMovementSpeed(), is(40));
    }

}