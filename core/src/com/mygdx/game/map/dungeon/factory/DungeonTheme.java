package com.mygdx.game.map.dungeon.factory;

import com.google.common.collect.ImmutableMap;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.monster.*;
import com.mygdx.game.actor.monster.greenskins.CaveTroll;
import com.mygdx.game.actor.monster.greenskins.Goblin;
import com.mygdx.game.actor.monster.greenskins.Orc;
import com.mygdx.game.actor.monster.undead.Lich;
import com.mygdx.game.actor.monster.undead.Skeleton;
import com.mygdx.game.actor.monster.undead.SkeletonWarrior;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public enum DungeonTheme {

    Undead(ImmutableMap.<MonsterTier, List<Class<? extends Actor>>>builder()
            .put(MonsterTier.Tier1, Arrays.asList(Skeleton.class))
            .put(MonsterTier.Tier2, Arrays.asList(SkeletonWarrior.class))
            .put(MonsterTier.Tier3, Collections.emptyList())
            .put(MonsterTier.Tier4, Arrays.asList(Lich.class))
            .put(MonsterTier.Boss, Collections.emptyList())
            .build()
    ),
    Greenskin(ImmutableMap.<MonsterTier, List<Class<? extends Actor>>>builder()
            .put(MonsterTier.Tier1, Arrays.asList(Goblin.class))
            .put(MonsterTier.Tier2, Arrays.asList(Orc.class))
            .put(MonsterTier.Tier3, Arrays.asList(CaveTroll.class))
            .put(MonsterTier.Tier4, Collections.emptyList())
            .put(MonsterTier.Boss, Collections.emptyList())
            .build());

    private Map<MonsterTier, List<Class<? extends Actor>>> monsters;

    DungeonTheme(Map<MonsterTier, List<Class<? extends Actor>>> monsters) {
        this.monsters = monsters;
    }

    public List<Class<? extends Actor>> getMonsters(MonsterTier tier) {
        return monsters.get(tier);
    }
}
