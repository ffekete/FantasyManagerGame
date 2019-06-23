package com.mygdx.game.rules.levelup;

import com.mygdx.game.actor.component.skill.Skill;
import com.mygdx.game.actor.component.skill.WeaponSkill;
import com.mygdx.game.actor.hero.Warrior;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SkillSorterImplUnitTest {

    @Test
    public void test() {
        Warrior warrior = new Warrior();
        warrior.getWeaponSkills().put(WeaponSkill.Sword, 2);
        warrior.getWeaponSkills().put(WeaponSkill.Dagger, 3);
        warrior.getWeaponSkills().put(WeaponSkill.Warhammer, 1);

        List<Skill> skills = Arrays.asList(WeaponSkill.Dagger, WeaponSkill.Sword, WeaponSkill.Warhammer);

        SkillSorterImpl.BY_CURRENT_LEVEL.sort(skills, warrior);

        assertThat(skills.get(0), is(WeaponSkill.Dagger));
        assertThat(skills.get(1), is(WeaponSkill.Sword));
        assertThat(skills.get(2), is(WeaponSkill.Warhammer));

    }

}