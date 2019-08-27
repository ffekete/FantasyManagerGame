package com.mygdx.game.resolver;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.util.Optional;

public class ModdablePathAudioResolver implements PathResolver<Sound> {

    @Override
    public Optional<Sound> resolve(String path) {

        try {
            FileHandle fileHandle = Gdx.files.internal("../mod/" + path);
            return Optional.of(Gdx.audio.newSound(fileHandle));
        } catch (Exception e) {

        }

        try {
            FileHandle fileHandle = Gdx.files.internal(path);
            return Optional.of(Gdx.audio.newSound(fileHandle));
        } catch (Exception e) {

        }

        return Optional.empty();
    }


}