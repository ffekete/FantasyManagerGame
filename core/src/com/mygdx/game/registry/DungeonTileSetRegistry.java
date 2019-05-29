package com.mygdx.game.registry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.google.common.collect.ImmutableMap;
import com.mygdx.game.map.dungeon.DungeonType;

import java.util.Map;

public class DungeonTileSetRegistry {

    public static final DungeonTileSetRegistry INSTANCE = new DungeonTileSetRegistry();

    private final Map<DungeonType, Texture> textures;

    private DungeonTileSetRegistry() {
        textures = ImmutableMap.<DungeonType, Texture>builder()
                .put(DungeonType.CAVE, new Texture(Gdx.files.internal("tilesets/cave.png")))
                .build();
    }

    public Texture getFor(DungeonType dungeonType) {
        return textures.get(dungeonType);
    }
}
