package com.mygdx.game.rules.levelup;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class ExperienceLevelMapper {

    public static final ExperienceLevelMapper INSTANCE = new ExperienceLevelMapper();

    private final Map<Long, Long> experienceTable = ImmutableMap.<Long, Long>builder()
            .put(1L, 1000L)
            .put(2L, 2200L)
            .put(3L, 3500L)
            .put(4L, 5500L)
            .put(5L, 8000L)
            .put(6L, 11000L)
            .put(7L, 14500L)
            .put(8L, 18500L)
            .put(9L, 24000L)
            .put(10L, 31000L)
            .put(11L, 40000L)
            .put(12L, 52000L)
            .put(13L, 77000L)
            .put(14L, 97000L)
            .build();

    public long getForLevel(long level) {
        return experienceTable.getOrDefault(level, 100000L);
    }

    public long getForExperiancePoints(long experiencePoints) {
        long value = 1;
        for(Map.Entry<Long, Long> l : experienceTable.entrySet()) {
            if(experiencePoints < l.getValue()) {
                return value;
            }
            value = l.getKey();
        }

        return value;
    }
}
