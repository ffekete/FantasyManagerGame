package com.mygdx.game.rules.levelup;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.component.skill.Skill;
import com.mygdx.game.actor.component.skill.WeaponSkill;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum SkillSorterImpl implements SkillSorter {

    BY_CURRENT_LEVEL() {
        @Override
        public void sort(List<Skill> skillList, Actor actor) {

            Collections.sort(skillList, new Comparator<Skill>() {
                @Override
                public int compare(Skill o1, Skill o2) {
                    int value = WeaponSkill.class.isAssignableFrom(o1.getClass()) ? actor.getWeaponSkills().get(o1) : actor.getMagicSkills().get(o1);
                    int value2 = WeaponSkill.class.isAssignableFrom(o1.getClass()) ? actor.getWeaponSkills().get(o2) : actor.getMagicSkills().get(o2);

                    return Integer.compare(value2, value);
                }
            });
        }
    }

}
