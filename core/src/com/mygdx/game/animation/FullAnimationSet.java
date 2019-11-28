package com.mygdx.game.animation;

import java.util.ArrayList;
import java.util.Map;

public class FullAnimationSet {

    private ArrayList<ArcheTypeAnimationSet> content;

    public ArrayList<ArcheTypeAnimationSet> getAnimationSets() {
        return content;
    }

    public void setAnimationSets(ArrayList<ArcheTypeAnimationSet> animationSets) {
        this.content = animationSets;
    }
}
