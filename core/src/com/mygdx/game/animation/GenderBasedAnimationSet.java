package com.mygdx.game.animation;

import java.util.ArrayList;

public class GenderBasedAnimationSet {

    private ArrayList<ArcheTypeAnimationSet> content;

    public ArrayList<ArcheTypeAnimationSet> getAnimationSets() {
        return content;
    }

    public void setAnimationSets(ArrayList<ArcheTypeAnimationSet> animationSets) {
        this.content = animationSets;
    }
}
