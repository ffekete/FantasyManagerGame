package com.mygdx.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

public class JsonReader {

    public FullAnimationSet read() {
        FullAnimationSet archeTypeAnimationSet = new Json().fromJson(FullAnimationSet.class, Gdx.files.internal("resources/data/character/appearance/appearance.json").readString());

        return archeTypeAnimationSet;
    }



}
