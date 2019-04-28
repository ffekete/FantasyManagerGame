package com.mygdx.game.registry;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.animation.Animation;
import com.mygdx.game.animation.AnimationBuilder;
import com.mygdx.game.logic.activity.Activity;

import java.util.HashMap;
import java.util.Map;

public class AnimationRegistry {

    public static final AnimationRegistry INSTANCE = new AnimationRegistry();

    private Map<Actor, Map<Class<? extends Activity>, Animation>> animations = new HashMap<>();

    public Map<Actor, Map<Class<? extends Activity>, Animation>> getAnimations() {
        return animations;
    }

    public Map<Class<? extends Activity>, Animation> get(Actor actor) {
        return animations.getOrDefault(actor, null);
    }

    public void add(Actor actor, Map<Class<? extends Activity>, Animation> animation) {
        animations.put(actor, animation);
    }

    public void refresh(Actor actor) {
        animations.put(actor, AnimationBuilder.INSTANCE.buildFor(actor));
    }

    public void remove(Actor actor) {
        animations.remove(actor);
    }
}
