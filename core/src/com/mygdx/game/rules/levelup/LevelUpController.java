package com.mygdx.game.rules.levelup;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.component.skill.MagicSkill;
import com.mygdx.game.actor.component.skill.Skill;
import com.mygdx.game.actor.component.skill.WeaponSkill;
import com.mygdx.game.actor.hero.Hero;
import com.mygdx.game.actor.hero.MeleeHero;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LevelUpController {

    private MonsterToExperienceMapper monsterToExperienceMapper = MonsterToExperienceMapper.INSTANCE;
    private ExperienceLevelMapper experienceLevelMapper = ExperienceLevelMapper.INSTANCE;

    public void calculate(Actor killer, Actor target) {

        if(!Hero.class.isAssignableFrom(killer.getClass()))
            return;

        long experience = monsterToExperienceMapper.getFor(target.getClass());
        System.out.println("gained experience " + experience + " " + killer.getLevel());

        int actualLevel = killer.getLevel();

        killer.addExperiencePoints(experience);

        int nextLevelAfterExpGain = (int)experienceLevelMapper.getForExperiancePoints(killer.getExperiencePoints());
        killer.setLevel((int)nextLevelAfterExpGain);
        int skillPointsToSpend = nextLevelAfterExpGain - actualLevel;

        while(skillPointsToSpend > 0) {
            // level up until there's no points left

            // get all skill focuses
            List<Skill> skillFocus = killer.getSkillFocusDefinition();

            // sort them by actual level for the target actor
            SkillSorterImpl.BY_CURRENT_LEVEL.sort(skillFocus, killer);

            // what skills are not maxed yet
            List<Skill> skillsNotMaxedAlready = skillFocus.stream().filter(skill -> killer.getSkillLevel(skill) < 6).collect(Collectors.toList());


            if(MeleeHero.class.isAssignableFrom(killer.getClass())) {
                if (skillsNotMaxedAlready.size() == 0) {
                    skillsNotMaxedAlready = Arrays.stream(WeaponSkill.values()).filter(weaponSkill -> killer.getSkillLevel(weaponSkill) < 6).collect(Collectors.toList());
                }

                if (skillsNotMaxedAlready.size() == 0) {
                    skillsNotMaxedAlready = Arrays.stream(MagicSkill.values()).filter(weaponSkill -> killer.getSkillLevel(weaponSkill) < 6).collect(Collectors.toList());
                }
            } else {
                if (skillsNotMaxedAlready.size() == 0) {
                    skillsNotMaxedAlready = Arrays.stream(MagicSkill.values()).filter(weaponSkill -> killer.getSkillLevel(weaponSkill) < 6).collect(Collectors.toList());
                }

                if (skillsNotMaxedAlready.size() == 0) {
                    skillsNotMaxedAlready = Arrays.stream(WeaponSkill.values()).filter(weaponSkill -> killer.getSkillLevel(weaponSkill) < 6).collect(Collectors.toList());
                }
            }

            if(skillsNotMaxedAlready.size() > 0) {
                Skill skillToLevelUp = null;

                for (Skill skill : skillsNotMaxedAlready) {
                    if (skillPointsToSpend + killer.getUnspentSkillPoints() >= SkillRequirementMapper.INSTANCE.getSkillRequirements().getOrDefault(killer.getSkillLevel(skill), 1)) {
                        skillToLevelUp = skill;
                        break;
                    }
                }

                // if there is one to spend the points on
                if(skillToLevelUp != null) {
                    System.out.println("Levelling up skill " + skillToLevelUp);
                    System.out.println("From: " + killer.getSkillLevel(skillToLevelUp));
                    int cost = SkillRequirementMapper.INSTANCE.getSkillRequirements().getOrDefault(killer.getSkillLevel(skillToLevelUp), 1);
                    killer.increaseSkillLevel(skillToLevelUp);

                    if(cost <= killer.getUnspentSkillPoints()) {
                        killer.setUnspentSkillPoints(killer.getUnspentSkillPoints() - cost);
                    } else {
                        killer.setUnspentSkillPoints(Math.max(killer.getUnspentSkillPoints() - cost, 0));
                        cost = cost - killer.getUnspentSkillPoints();

                        skillPointsToSpend -= cost;
                    }
                    System.out.println("To: " + killer.getSkillLevel(skillToLevelUp));
                    continue;
                } else {
                    killer.setUnspentSkillPoints(killer.getUnspentSkillPoints() + skillPointsToSpend);
                    return;
                }
            } else {
                killer.setUnspentSkillPoints(killer.getUnspentSkillPoints() + skillPointsToSpend);
                return;
            }

            //
        }

    }
}
