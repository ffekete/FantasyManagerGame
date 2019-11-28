package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.component.skill.WeaponSkill;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.monster.CaveTroll;
import com.mygdx.game.item.armor.BlackPlateMail;
import com.mygdx.game.item.armor.ChainMailArmor;
import com.mygdx.game.item.armor.LeatherArmor;
import com.mygdx.game.item.shield.MediumShield;
import com.mygdx.game.item.shield.SmallShiled;
import com.mygdx.game.item.weapon.bow.LongBow;
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
    public void testShouldPass_betterArmorInInventory() {
        Actor actor = new Warrior();
        actor.equip(new LeatherArmor());
        actor.getInventory().add(new ChainMailArmor());
        Decision decision = new EquipDecision();
        boolean result = decision.decide(actor);
        assertThat(result, is(true));
    }

    @Test
    public void testShouldFail_betterArmorInInventoryButDoesNotFit() {
        Actor actor = new CaveTroll();

        actor.getInventory().add(new ChainMailArmor());
        Decision decision = new EquipDecision();
        boolean result = decision.decide(actor);
        assertThat(result, is(false));
    }

    @Test
    public void testShouldFail_worseArmorInInventory() {
        Actor actor = new Warrior();
        actor.equip(new ChainMailArmor());
        actor.getInventory().add(new LeatherArmor());
        Decision decision = new EquipDecision();
        boolean result = decision.decide(actor);
        assertThat(result, is(false));
    }

    @Test
    public void testShouldFail_equalArmorInInventory() {
        Actor actor = new Warrior();
        actor.equip(new LeatherArmor());
        actor.getInventory().add(new LeatherArmor());
        Decision decision = new EquipDecision();
        boolean result = decision.decide(actor);
        assertThat(result, is(false));
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
    public void testShouldFail_shield_twoHandedWeaponInRightHand() {
        Actor actor = new Warrior();
        actor.equip(new LongBow());
        actor.getInventory().add(new SmallShiled());

        Decision decision = new EquipDecision();
        boolean result = decision.decide(actor);
        assertThat(result, is(false));
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
    public void testShouldFail_shield_sameIsInInventory() {
        Actor actor = new Warrior();
        actor.equip(new MediumShield());
        actor.getInventory().add(new MediumShield());

        Decision decision = new EquipDecision();
        boolean result = decision.decide(actor);
        assertThat(result, is(false));
    }

    @Test
    public void testShouldPass_betterWeaponSkillIsForNewItem_betterItemEquipped() {
        Actor actor = new Warrior();
        actor.equip(new FlameTongue());
        actor.getInventory().add(new LongBow());

        actor.getWeaponSkills().put(WeaponSkill.Sword, 0);
        actor.getWeaponSkills().put(WeaponSkill.Bow, 1);

        Decision decision = new EquipDecision();
        boolean result = decision.decide(actor);
        assertThat(result, is(true));
    }

    @Test
    public void testShouldPass_betterWeaponSkillIsForNewItem() {
        Actor actor = new Warrior();
        actor.equip(new ShortSword());
        actor.getInventory().add(new LongBow());

        actor.getWeaponSkills().put(WeaponSkill.Sword, 0);
        actor.getWeaponSkills().put(WeaponSkill.Bow, 1);

        Decision decision = new EquipDecision();
        boolean result = decision.decide(actor);
        assertThat(result, is(true));
    }

    @Test
    public void testShouldFail_equalWeaponSkillIsForNewItem_sameItemInInventory() {
        Actor actor = new Warrior();
        actor.equip(new LongBow());
        actor.getInventory().add(new LongBow());

        actor.getWeaponSkills().put(WeaponSkill.Bow, 1);

        Decision decision = new EquipDecision();
        boolean result = decision.decide(actor);
        assertThat(result, is(false));
    }

    @Test
    public void testShouldPass_equalWeaponSkillIsForNewItem_morePowerfulItemInInventory() {
        Actor actor = new Warrior();
        actor.equip(new ShortSword());
        actor.getInventory().add(new LongBow());

        actor.getWeaponSkills().put(WeaponSkill.Sword, 1);
        actor.getWeaponSkills().put(WeaponSkill.Bow, 1);

        Decision decision = new EquipDecision();
        boolean result = decision.decide(actor);
        assertThat(result, is(true));
    }

    @Test
    public void testShouldFail_equalWeaponSkillIsForNewItemEqualItem() {
        Actor actor = new Warrior();
        actor.equip(new ShortSword());
        actor.getInventory().add(new ShortSword());

        actor.getWeaponSkills().put(WeaponSkill.Sword, 1);
        actor.getWeaponSkills().put(WeaponSkill.Bow, 1);

        Decision decision = new EquipDecision();
        boolean result = decision.decide(actor);
        assertThat(result, is(false));
    }
}