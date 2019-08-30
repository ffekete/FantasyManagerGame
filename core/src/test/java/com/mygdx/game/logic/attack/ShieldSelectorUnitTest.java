package com.mygdx.game.logic.attack;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.item.shield.SmallShiled;
import com.mygdx.game.item.weapon.sword.ShortSword;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ShieldSelectorUnitTest {

    private final ShieldSelector shieldSelector = new ShieldSelector();

    @Test
    public void testForShield() {
        Actor actor = new Warrior();
        SmallShiled smallShiled = new SmallShiled();
        actor.setLeftHandItem(smallShiled);

        assertThat(shieldSelector.getShieldValue(actor), is(smallShiled.getDefense()));
    }

    @Test
    public void testForNoShield() {
        Actor actor = new Warrior();

        assertThat(shieldSelector.getShieldValue(actor), is(0));
    }

    @Test
    public void testForWeaponInLeftHand() {
        Actor actor = new Warrior();
        actor.setLeftHandItem(new ShortSword());
        actor.setRightHandItem(new ShortSword());

        assertThat(shieldSelector.getShieldValue(actor), is(0));
    }
}