package com.mygdx.game.registry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.google.common.collect.ImmutableMap;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.map.Map2D;

import java.util.Map;

public class SoundRegistry {

    public static final SoundRegistry INSTANCE = new SoundRegistry();

    private final Map<Class<? extends Activity>, Sound> musics = ImmutableMap.<Class<? extends Activity>, Sound>builder()
            .put(MovementActivity.class, Gdx.audio.newSound(Gdx.files.internal("sounds/footsteps.mp3")))
            .build();


    public Sound getFor(Class<? extends Activity> activity) {
        return musics.get(activity);
    }

}
