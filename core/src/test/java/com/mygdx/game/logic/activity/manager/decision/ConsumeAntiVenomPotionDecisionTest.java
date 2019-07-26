package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Wizard;
import com.mygdx.game.effect.Poison;
import com.mygdx.game.item.potion.SmallAntiVenomPotion;
import com.mygdx.game.item.weapon.sword.ShortSword;
import com.mygdx.game.logic.activity.manager.ActivityManager;
import com.mygdx.game.logic.activity.single.ConsumeAntiVenomPotion;
import com.mygdx.game.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.map.dungeon.Dungeon;
import com.mygdx.game.registry.EffectRegistry;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ConsumeAntiVenomPotionDecisionTest {

    @Test
    public void testForDecisionManagement_shouldDecideToConsume() {

        Dungeon dungeon = new DummyDungeonCreator().create(3);

        Actor actor = new Wizard();
        actor.equip(new ShortSword());
        actor.setCurrentMap(dungeon);
        actor.getInventory().add(new SmallAntiVenomPotion());

        EffectRegistry.INSTANCE.add(new Poison(5,5, actor, actor), actor);

        new ActivityManager().manage(actor);

        assertThat(actor.getActivityStack().getSize(), is(2));
        assertThat(actor.getActivityStack().contains(ConsumeAntiVenomPotion.class), is(true));

        new ActivityManager().manage(actor);

        assertThat(actor.getActivityStack().getSize(), is(2));
        assertThat(actor.getActivityStack().contains(ConsumeAntiVenomPotion.class), is(true));
    }

}