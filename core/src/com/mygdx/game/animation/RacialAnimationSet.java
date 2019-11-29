package com.mygdx.game.animation;

import com.mygdx.game.actor.Gender;

import java.util.HashMap;
import java.util.Map;

public class RacialAnimationSet {

    String type;
    Map<Gender, ArcheTypeAnimationSet> animations;

    ArcheTypeAnimationSet male;
    ArcheTypeAnimationSet female;

    public void update() {
        animations = new HashMap<>();
        animations.put(Gender.Male, male);
        animations.put(Gender.Female, female);
    }

    public Map<Gender, ArcheTypeAnimationSet> getRacialAnimations() {
        return animations;
    }

    public String getType() {
        return type;
    }
}
