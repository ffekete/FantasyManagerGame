package com.mygdx.game.registry;

import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.animation.ActorAnimation;
import com.mygdx.game.animation.Animation;
import com.mygdx.game.animation.AnimationBuilder;

import java.util.HashMap;
import java.util.Map;

public class AnimationRegistry implements Disposable {

    public static final AnimationRegistry INSTANCE = new AnimationRegistry();

    private Map<Actor, ActorAnimation> animations = new HashMap<>();

    public Map<Actor, ActorAnimation> getAnimations() {
        return animations;
    }

    public ActorAnimation get(Actor actor) {
        return animations.getOrDefault(actor, null);
    }

    public void add(Actor actor, ActorAnimation animation) {
        animations.put(actor, animation);
    }

    public void refresh(Actor actor) {
        animations.put(actor, AnimationBuilder.INSTANCE.build(actor));
    }

    public void remove(Actor actor) {
        animations.remove(actor);
    }

    @Override
    public void dispose() {
        for(ActorAnimation a: animations.values()) {
            a.dispose();
        }
    }
}
