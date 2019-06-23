package com.mygdx.game.actor.factory;

import com.mygdx.game.actor.component.skill.Skill;
import com.mygdx.game.actor.component.skill.WeaponSkill;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SkillFocusFactoryUnitTest {

    @Test
    public void testForShuffle() {
        List<Skill> skillList = SkillFocusFactory.Warrior.create();

        assertThat(skillList.size(), is(2));
    }

    @Test
    public void testForRanger() {
        List<Skill> skillList = SkillFocusFactory.Ranger.create();

        assertThat(skillList.size(), is(2));
        assertThat(skillList.contains(WeaponSkill.Bow), is(true));
    }

}