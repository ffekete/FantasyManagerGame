package com.mygdx.game.rules.levelup;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Maps the requirements for skills, e.g. level 1 skill costs 1 level up points to upgrade it to level 2 skill.
 */
public class SkillRequirementMapper {

    public static final SkillRequirementMapper INSTANCE = new SkillRequirementMapper();

    private final Map<Integer, Integer> skillRequirements = ImmutableMap.<Integer, Integer>builder()
            .put(1, 1)
            .put(2, 1)
            .put(3, 2)
            .put(4, 2)
            .put(5, 3)
            .put(6, 4)
            .build();

    public Map<Integer, Integer> getSkillRequirements() {
        return skillRequirements;
    }
}
