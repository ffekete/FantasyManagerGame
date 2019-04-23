package com.mygdx.game.registry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.google.common.collect.ImmutableMap;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.monster.Goblin;

import java.util.Map;

public class TextureRegistry {

    public static final TextureRegistry INSTANCE = new TextureRegistry();

    private Map<Class, Texture> textures;

    public TextureRegistry() {
        textures = ImmutableMap.<Class, Texture>builder()
                .put(Warrior.class, new Texture(Gdx.files.internal("warrior.png")))
                .put(Goblin.class, new Texture(Gdx.files.internal("goblin.png")))
                .build();
    }

    public Texture getFor(Class clazz) {
        if(textures.containsKey(clazz)) {
            return textures.get(clazz);
        }
        return null;
    }
}
