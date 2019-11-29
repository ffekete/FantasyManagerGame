package com.mygdx.game.animation;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;

public class JsonReader {

    public FullAnimationSet read() {
        FullAnimationSet archeTypeAnimationSet = new Gson().fromJson(Gdx.files.internal("resources/data/character/appearance/appearance.json").readString(), FullAnimationSet.class);

        archeTypeAnimationSet.getAnimationSets().forEach(RacialAnimationSet::update);

        return archeTypeAnimationSet;
    }



}
