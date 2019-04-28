package com.mygdx.game.registry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.google.common.collect.ImmutableMap;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.animation.ActorAnimation;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.IdleActivity;
import com.mygdx.game.logic.activity.MovementActivity;
import com.mygdx.game.logic.activity.PickUpItemActivity;
import com.mygdx.game.animation.Animation;

import java.util.Map;

public class AnimationRegistry {

    public static final AnimationRegistry INSTANCE = new AnimationRegistry();

    Map<Class, Map<Class<? extends Activity>, Animation>> animations = ImmutableMap.<Class, Map<Class<? extends Activity>, Animation>>builder()
            .put(Warrior.class,
                    ImmutableMap.<Class<? extends Activity>, Animation> builder()
                            .put(MovementActivity.class, ActorAnimation.builder()
                                    .withHead(new Texture(Gdx.files.internal("head_idle.png")))
                                    .withTorso(new Texture(Gdx.files.internal("torso_idle.png")))
                                    .withLegs(new Texture(Gdx.files.internal("leg_moving.png")))
                                    .withArms(new Texture(Gdx.files.internal("hand_idle.png")))
                                    .build()
                            )
                            .put(IdleActivity.class, ActorAnimation.builder()
                                    .withHead(new Texture(Gdx.files.internal("head_idle.png")))
                                    .withTorso(new Texture(Gdx.files.internal("torso_idle.png")))
                                    .withLegs(new Texture(Gdx.files.internal("leg_idle.png")))
                                    .withArms(new Texture(Gdx.files.internal("hand_idle.png")))
                                    .build()
                            )
                            .put(PickUpItemActivity.class, ActorAnimation.builder()
                                    .withHead(new Texture(Gdx.files.internal("head_idle.png")))
                                    .withTorso(new Texture(Gdx.files.internal("torso_idle.png")))
                                    .withLegs(new Texture(Gdx.files.internal("leg_idle.png")))
                                    .withArms(new Texture(Gdx.files.internal("hand_idle.png")))
                                    .build()
                            )
                            .build()
            )
            .build();

    public Map<Class, Map<Class<? extends Activity>, Animation>> getAnimations() {
        return animations;
    }
}
