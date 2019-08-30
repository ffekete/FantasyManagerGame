package com.mygdx.game.registry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.google.common.collect.ImmutableMap;
import com.mygdx.game.map.Map2D;

import java.util.Map;

public class MusicRegistry {

    public static final MusicRegistry INSTANCE = new MusicRegistry();

    private final Map<Map2D.MapType, Sound> musics = ImmutableMap.<Map2D.MapType, Sound>builder()
            .put(Map2D.MapType.DUNGEON_CAVE, Gdx.audio.newSound(Gdx.files.internal("sounds/music/cave.ogg")))
            .put(Map2D.MapType.DUNGEON_ROOMS, Gdx.audio.newSound(Gdx.files.internal("sounds/music/cave.ogg")))
            .put(Map2D.MapType.WORLD_MAP, Gdx.audio.newSound(Gdx.files.internal("sounds/music/medieval-village.ogg")))
            .build();


    public Sound getFor(Map2D.MapType dungeonType) {
        return musics.get(dungeonType);
    }

}
