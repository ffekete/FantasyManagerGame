package com.mygdx.game.rules.levelup;

import com.google.common.collect.ImmutableMap;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Hero;
import com.mygdx.game.actor.monster.*;

import java.util.Map;

/**
 * Maps enemy classes and experience points to earn when one of these are killed.
 */
public class MonsterToExperienceMapper {

    public static final MonsterToExperienceMapper INSTANCE = new MonsterToExperienceMapper();

    private final Map<Class<? extends Actor>, Long> monsterToExperianceMap = ImmutableMap.<Class<? extends Actor>, Long>builder()
            .put(Orc.class, 100L)
            .put(Goblin.class, 50L)
            .put(Skeleton.class, 70L)
            .put(SkeletonWarrior.class, 100L)
            .put(Lich.class, 1000L)
            .build();

    public long getFor(Class<? extends Actor> clazz) {
        if(Hero.class.isAssignableFrom(clazz)) {
            return 0; // friendly kill doesn't count, sorry
        }
        return monsterToExperianceMap.get(clazz);
    }
}
