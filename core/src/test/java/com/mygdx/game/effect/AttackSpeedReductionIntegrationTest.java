package com.mygdx.game.effect;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.effect.manager.EffectManager;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.EffectRegistry;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AttackSpeedReductionIntegrationTest {


    public static final float GDX_DELTA_TIME = 0.25f;
    private ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    private EffectRegistry effectRegistry = EffectRegistry.INSTANCE;
    private EffectManager effectManager = EffectManager.INSTANCE;

    @Test
    public void testForDamage() {

        Map2D dungeon = new DummyDungeonCreator().create(3);
        Actor actor = new Warrior();
        actorRegistry.add(dungeon, actor);
        actor.setHp(5);
        int attackSpeed = actor.getAttackSpeed();
        AttackSpeedReduction attackSpeedReduction = new AttackSpeedReduction(5);
        effectRegistry.add(attackSpeedReduction, actor);

        for (int i = 0; i < 60 / (60 * GDX_DELTA_TIME); i++) {
            effectManager.update();
        }

        assertThat(actor.getAttackSpeed(), is(attackSpeed - 5));

        effectRegistry.remove(attackSpeedReduction, actor);
        assertThat(actor.getAttackSpeed(), is(attackSpeed));
    }
}