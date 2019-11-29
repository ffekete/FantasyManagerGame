package com.mygdx.game.animation;

import java.util.ArrayList;

public class FullAnimationSet {

    private ArrayList<RacialAnimationSet> content;

    public ArrayList<RacialAnimationSet> getAnimationSets() {
        return content;
    }

    public void setAnimationSets(ArrayList<RacialAnimationSet> animationSets) {
        this.content = animationSets;
    }
}
