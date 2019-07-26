package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.monster.Lich;
import com.mygdx.game.effect.Poison;
import com.mygdx.game.item.potion.SmallAntiVenomPotion;
import com.mygdx.game.registry.EffectRegistry;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ConsumeAntiVenomPotionTest {

    @Test
    public void testForCosume() {
        Actor actor = new Warrior();
        Actor lich = new Lich();

        EffectRegistry.INSTANCE.add(new Poison(5, 10, actor, lich), actor);
        EffectRegistry.INSTANCE.add(new Poison(6, 10, actor, lich), actor);

        assertThat(EffectRegistry.INSTANCE.hasEffect(actor, Poison.class).isPresent(), is(true));

        new SmallAntiVenomPotion().consume(actor);

        assertThat(EffectRegistry.INSTANCE.hasEffect(actor, Poison.class).isPresent(), is(false));
    }

}