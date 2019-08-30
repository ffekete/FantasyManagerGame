package com.mygdx.game.actor.factory;

import com.mygdx.game.actor.component.skill.MagicSkill;
import com.mygdx.game.actor.component.skill.Skill;
import com.mygdx.game.actor.component.skill.WeaponSkill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum SkillFocusFactory implements SkillPriorityFactory {

    Warrior {
        @Override
        public List<Skill> create() {
            List<Skill> weaponSkills = Arrays.asList(WeaponSkill.Sword, WeaponSkill.Flail, WeaponSkill.Mace, WeaponSkill.TwoHandedSwords, WeaponSkill.Warhammer);
            Collections.shuffle(weaponSkills);

            // keeping two skills
            return weaponSkills.subList(0, 2);
        }
    },
    Wizard {
        @Override
        public List<Skill> create() {
            List<Skill> skills = Arrays.asList(MagicSkill.AirMagic, MagicSkill.FireMagic, MagicSkill.EarthMagic, MagicSkill.ColdMagic);
            Collections.shuffle(skills);

            // keeping two skills
            return skills.subList(0, 2);
        }
    },
    Ranger {
        @Override
        public List<Skill> create() {
            List<Skill> weaponSkills =  new ArrayList<>();

            weaponSkills.add(WeaponSkill.Sword);
            weaponSkills.add(WeaponSkill.Dagger);
            Collections.shuffle(weaponSkills);
            weaponSkills.remove(1);
            weaponSkills.add(WeaponSkill.Bow);

            // keeping two skills
            return weaponSkills.subList(0, 2);
        }
    },
    Priest {
        @Override
        public List<Skill> create() {
            List<Skill> skills =  new ArrayList<>();

            skills.add(WeaponSkill.Warhammer);
            skills.add(WeaponSkill.Staff);

            Collections.shuffle(skills);
            skills.remove(0);

            skills.add(MagicSkill.LifeMagic);
            skills.add(MagicSkill.ProtectiveMagic);

            Collections.shuffle(skills);
            skills.remove(1);
            skills.add(WeaponSkill.Bow);

            return skills.subList(0, 3);
        }
    },
    Rogue {
        @Override
        public List<Skill> create() {
            List<Skill> skills =  new ArrayList<>();

            skills.add(WeaponSkill.Sword);
            skills.add(WeaponSkill.Dagger);
            skills.add(WeaponSkill.Bow);

            Collections.shuffle(skills);

            // keeping two skills
            return skills.subList(0, 2);
        }
    },
}
