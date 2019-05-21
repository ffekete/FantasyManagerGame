package com.mygdx.game.logic.attack;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.component.WeaponSkill;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.item.weapon.sword.ShortSword;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WeaponWeaponSkillSelectorUnitTest {

    private final WeaponSkillSelector weaponSkillSelector = new WeaponSkillSelector();

    @Test
    public void testForSword() {
        ShortSword shortSword = new ShortSword();
        Actor actor = new Warrior();
        actor.setLeftHandItem(shortSword);

        actor.getWeaponSkills().put(WeaponSkill.Sword, 5);

        assertThat(weaponSkillSelector.findBestSkillFor(actor, shortSword), is(5f));
    }

    @Test
    public void testForSword2() {
        ShortSword shortSword = new ShortSword();
        Actor actor = new Warrior();
        actor.setLeftHandItem(shortSword);

        actor.getWeaponSkills().put(WeaponSkill.Sword, 1);

        assertThat(weaponSkillSelector.findBestSkillFor(actor, shortSword), is(1f));
    }

    @Test
    public void testForSword_DaggerIsHigher() {
        ShortSword shortSword = new ShortSword();
        Actor actor = new Warrior();
        actor.setLeftHandItem(shortSword);

        actor.getWeaponSkills().put(WeaponSkill.Sword, 2);
        actor.getWeaponSkills().put(WeaponSkill.Dagger, 5);

        assertThat(weaponSkillSelector.findBestSkillFor(actor, shortSword), is(2.5f));
    }
}