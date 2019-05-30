package com.mygdx.game.effect;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.effect.manager.EffectManager;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.EffectRegistry;
import com.mygdx.game.util.GdxGraphicsDecorator;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PoisonIntegrationTest {

    public static final float GDX_DELTA_TIME = 0.25f;
    private ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    private EffectRegistry effectRegistry = EffectRegistry.INSTANCE;
    private EffectManager effectManager = EffectManager.INSTANCE;

    @Before
    public void setUp() {
        Gdx.graphics = new GdxGraphicsDecorator();
    }

    @Test
    public void testForDamage() {

        Map2D dungeon = new DummyDungeonCreator().create(3);
        Actor actor = new Warrior();
        actorRegistry.add(dungeon, actor);
        actor.setHp(5);
        effectRegistry.add(new Poison(1,5,actor, actor), actor);

        for (int i = 0; i < 60 / (60 * GDX_DELTA_TIME); i++) {
            effectManager.update();
        }

        assertThat(actor.getHp(), is(4));
    }

    @Test
    public void testForDamage2() {

        Map2D dungeon = new DummyDungeonCreator().create(3);
        Actor actor = new Warrior();
        actorRegistry.add(dungeon, actor);
        actor.setHp(5);
        effectRegistry.add(new Poison(2,5,actor, actor), actor);

        for (int i = 0; i < 60 / (60 * GDX_DELTA_TIME); i++) {
            effectManager.update();
        }

        assertThat(actor.getHp(), is(3));
    }

    @Test
    public void testForDamage0() {

        Map2D dungeon = new DummyDungeonCreator().create(3);
        Actor actor = new Warrior();
        actorRegistry.add(dungeon, actor);
        actor.setHp(5);
        effectRegistry.add(new Poison(0,5,actor, actor), actor);

        for (int i = 0; i < 60 / (60 * GDX_DELTA_TIME); i++) {
            effectManager.update();
        }

        assertThat(actor.getHp(), is(5));
    }
}