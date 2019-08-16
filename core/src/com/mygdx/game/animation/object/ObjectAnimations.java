package com.mygdx.game.animation.object;

import com.mygdx.game.registry.TextureRegistry;

public class ObjectAnimations {

    public static final ObjectAnimations INSTANCE = new ObjectAnimations();

    private final TextureRegistry textureRegistry = TextureRegistry.INSTANCE;



    private ObjectAnimations() {
    }


}
