package com.mygdx.game.logic.action;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.google.common.collect.ImmutableMap;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.monster.*;

import java.util.Map;

public class BloodEffectSelector {

    public static final BloodEffectSelector INSTANCE = new BloodEffectSelector();

    private final Map<Class <? extends Actor>, Texture> textures = ImmutableMap.<Class <? extends Actor>, Texture>builder()
            .put(Skeleton.class, new Texture(Gdx.files.internal("effects/BoneDustEffect.png")))
            .put(SkeletonWarrior.class, new Texture(Gdx.files.internal("effects/BoneDustEffect.png")))
            .put(Lich.class, new Texture(Gdx.files.internal("effects/BoneDustEffect.png")))
            .put(Warrior.class, new Texture(Gdx.files.internal("effects/BloodEffect.png")))
            .put(Goblin.class, new Texture(Gdx.files.internal("effects/BloodEffect.png")))
            .put(Orc.class, new Texture(Gdx.files.internal("effects/BloodEffect.png")))
            .build();


    public Texture selectFor(Class<? extends Actor> clazz) {
        return textures.getOrDefault(clazz, new Texture(Gdx.files.internal("effects/BloodEffect.png")));
    }


}
