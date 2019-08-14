package com.mygdx.game.registry;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.animation.ActorAnimation;
import com.mygdx.game.animation.Animation;
import com.mygdx.game.object.AnimatedObject;

import java.util.HashMap;
import java.util.Map;

public class AnimationRegistry {

    public static final AnimationRegistry INSTANCE = new AnimationRegistry();

    private final Map<Actor, ActorAnimation> animations = new HashMap<>();
    private final Map<AnimatedObject, Animation> objectAnimations = new HashMap<>();

    public Map<Actor, ActorAnimation> getAnimations() {
        return animations;
    }

    public ActorAnimation get(Actor actor) {
        return animations.getOrDefault(actor, null);
    }

    public Animation get(AnimatedObject animatedObject) {
        return objectAnimations.get(animatedObject);
    }

    public void add(AnimatedObject object, Animation animation) {
        objectAnimations.put(object, animation);
    }

    public void add(Actor actor, ActorAnimation animation) {
        animations.put(actor, animation);
    }

    public void remove(Actor actor) {
        animations.remove(actor);
    }

    public void remove(AnimatedObject worldObject) {
        objectAnimations.remove(worldObject);
    }
}
