package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.item.armor.BlackPlateMail;
import com.mygdx.game.item.shield.MediumShield;
import com.mygdx.game.item.shield.SmallShiled;
import com.mygdx.game.item.weapon.sword.FlameTongue;
import com.mygdx.game.item.weapon.sword.ShortSword;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EquipDecisionTest {

    @Test
    public void testShouldPass_noWeapon() {
        Actor actor = new Warrior();
        Decision decision = new EquipDecision();
        boolean result = decision.decide(actor);
        assertThat(result, is(false));
    }

    @Test
    public void testShouldPass_armor() {
        Actor actor = new Warrior();
        actor.getInventory().add(new BlackPlateMail());
        Decision decision = new EquipDecision();
        boolean result = decision.decide(actor);
        assertThat(result, is(true));
    }

    @Test
    public void testShouldPass_weapon() {
        Actor actor = new Warrior();
        actor.getInventory().add(new ShortSword());

        Decision decision = new EquipDecision();
        boolean result = decision.decide(actor);
        assertThat(result, is(true));
    }

    @Test
    public void testShouldPass_weapon_betterIsInInventory() {
        Actor actor = new Warrior();
        actor.equip(new ShortSword());
        actor.getInventory().add(new FlameTongue());

        Decision decision = new EquipDecision();
        boolean result = decision.decide(actor);
        assertThat(result, is(true));
    }

    @Test
    public void testShouldFail_weapon_weakerIsInInventory() {
        Actor actor = new Warrior();
        actor.equip(new FlameTongue());
        actor.getInventory().add(new ShortSword());

        Decision decision = new EquipDecision();
        boolean result = decision.decide(actor);
        assertThat(result, is(false));
    }

    @Test
    public void testShouldPass_weapon_sameIsInInventory() {
        Actor actor = new Warrior();
        actor.equip(new FlameTongue());
        actor.getInventory().add(new FlameTongue());

        Decision decision = new EquipDecision();
        boolean result = decision.decide(actor);
        assertThat(result, is(false));
    }



    @Test
    public void testShouldPass_shield() {
        Actor actor = new Warrior();
        actor.getInventory().add(new SmallShiled());

        Decision decision = new EquipDecision();
        boolean result = decision.decide(actor);
        assertThat(result, is(true));
    }

    @Test
    public void testShouldPass_shield_betterIsInInventory() {
        Actor actor = new Warrior();
        actor.equip(new ShortSword());
        actor.getInventory().add(new MediumShield());

        Decision decision = new EquipDecision();
        boolean result = decision.decide(actor);
        assertThat(result, is(true));
    }

    @Test
    public void testShouldFail_shield_weakerIsInInventory() {
        Actor actor = new Warrior();
        actor.equip(new MediumShield());
        actor.getInventory().add(new SmallShiled());

        Decision decision = new EquipDecision();
        boolean result = decision.decide(actor);
        assertThat(result, is(false));
    }

    @Test
    public void testShouldPass_shield_sameIsInInventory() {
        Actor actor = new Warrior();
        actor.equip(new MediumShield());
        actor.getInventory().add(new MediumShield());

        Decision decision = new EquipDecision();
        boolean result = decision.decide(actor);
        assertThat(result, is(false));
    }
}