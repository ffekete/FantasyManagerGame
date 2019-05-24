package com.mygdx.game.animation.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.google.common.collect.ImmutableMap;
import com.mygdx.game.object.AnimatedObject;
import com.mygdx.game.object.decoration.SpiderWeb;
import com.mygdx.game.object.decoration.StandingTorch;

import java.util.Map;

public class ObjectAnimations {

    public static final ObjectAnimations INSTANCE = new ObjectAnimations();

    private final Map<Class<? extends AnimatedObject>, Texture> animationMap = ImmutableMap.<Class<? extends AnimatedObject>, Texture>builder()
            .put(StandingTorch.class, new Texture(Gdx.files.internal("object/StandingTorch.png")))
            .put(SpiderWeb.class, new Texture(Gdx.files.internal("object/SpiderWeb.png")))
            .build();

    private ObjectAnimations() {
    }

    public Texture getFor(Class<? extends AnimatedObject> clazz) {
        return animationMap.get(clazz);
    }
}
