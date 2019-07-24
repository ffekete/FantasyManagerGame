package com.mygdx.game.animation.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.google.common.collect.ImmutableMap;
import com.mygdx.game.object.AnimatedObject;
import com.mygdx.game.object.decoration.SpiderWeb;
import com.mygdx.game.object.decoration.StandingTorch;
import com.mygdx.game.registry.TextureRegistry;

import java.util.Map;

public class ObjectAnimations {

    public static final ObjectAnimations INSTANCE = new ObjectAnimations();

    private final TextureRegistry textureRegistry = TextureRegistry.INSTANCE;



    private ObjectAnimations() {
    }


}
