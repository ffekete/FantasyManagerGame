package com.mygdx.game.resolver;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.util.Optional;

public class ModdablePathResolver implements PathResolver {

    @Override
    public Optional<Texture> resolve(String path) {

        try {
            FileHandle fileHandle = Gdx.files.external("mod/" + path);
            return Optional.of(new Texture(fileHandle));
        } catch (Exception e) {

        }

        try {
            FileHandle fileHandle = Gdx.files.internal(path);
            return Optional.of(new Texture(fileHandle));
        } catch (Exception e) {

        }

        return Optional.empty();
    }


}