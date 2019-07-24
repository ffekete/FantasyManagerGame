package com.mygdx.game.logic.action;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.google.common.collect.ImmutableMap;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.monster.*;
import com.mygdx.game.resolver.ModdablePathResolver;

import java.util.Map;
import java.util.Optional;

public class BloodEffectSelector {

    public static final BloodEffectSelector INSTANCE = new BloodEffectSelector();

    private final ModdablePathResolver moddablePathResolver = new ModdablePathResolver();

    private final Map<Class <? extends Actor>, Optional<Texture>> textures = ImmutableMap.<Class <? extends Actor>, Optional<Texture>>builder()
            .put(Skeleton.class, moddablePathResolver.resolve("effects/BoneDustEffect.png"))
            .put(SkeletonWarrior.class, moddablePathResolver.resolve("effects/BoneDustEffect.png"))
            .put(Lich.class, moddablePathResolver.resolve("effects/BoneDustEffect.png"))
            .put(Warrior.class, moddablePathResolver.resolve("effects/BloodEffect.png"))
            .put(Goblin.class, moddablePathResolver.resolve("effects/BloodEffect.png"))
            .put(Orc.class, moddablePathResolver.resolve("effects/BloodEffect.png"))

            // default one
            .put(Actor.class, moddablePathResolver.resolve("effects/BloodEffect.png"))
            .build();

    public Optional<Texture> selectFor(Class<? extends Actor> clazz) {
        return textures.getOrDefault(clazz, textures.get(Actor.class));
    }


}
