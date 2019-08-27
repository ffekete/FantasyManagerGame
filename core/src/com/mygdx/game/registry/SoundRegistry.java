package com.mygdx.game.registry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.google.common.collect.ImmutableMap;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.resolver.ModdablePathAudioResolver;
import com.mygdx.game.resolver.ModdablePathResolver;

import java.util.Map;
import java.util.Optional;

public class SoundRegistry {

    public static final SoundRegistry INSTANCE = new SoundRegistry();
    private ModdablePathAudioResolver moddablePathResolver = new ModdablePathAudioResolver();

    private final Map<Class<? extends Activity>, Optional<Sound>> musics = ImmutableMap.<Class<? extends Activity>, Optional<Sound>>builder()
            .put(MovementActivity.class, moddablePathResolver.resolve("sounds/footsteps.mp3"))
            .build();


    public Optional<Sound> getFor(Class<? extends Activity> activity) {
        return musics.get(activity);
    }

}
