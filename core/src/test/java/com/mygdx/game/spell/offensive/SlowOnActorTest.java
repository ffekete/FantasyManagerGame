package com.mygdx.game.spell.offensive;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Wizard;
import com.mygdx.game.actor.monster.Skeleton;
import com.mygdx.game.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.map.dungeon.Dungeon;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.EffectRegistry;
import com.mygdx.game.registry.SpellRegistry;
import com.mygdx.game.spell.Spell;
import com.mygdx.game.spell.manager.SpellManager;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SlowOnActorTest {

    @Test
    public void test() {

        Actor actor = new Wizard();
        Actor target = new Skeleton();

        Dungeon dungeon = (Dungeon) new DummyDungeonCreator().create(3);

        actor.setCurrentMap(dungeon);

        ActorRegistry.INSTANCE.add(dungeon, actor);

        assertThat(actor.getAttackSpeed(), is(50));
        assertThat(actor.getMovementSpeed(), is(40));

        Spell slow = new Slow();
        slow.init(actor, target);
        SpellRegistry.INSTANCE.add(slow);

        SpellManager.INSTANCE.update();

        assertThat(EffectRegistry.INSTANCE.getAll(target).size(), is(2));
        assertThat(target.getAttackSpeed(), is(80));
        assertThat(target.getMovementSpeed(), is(100));
    }

}