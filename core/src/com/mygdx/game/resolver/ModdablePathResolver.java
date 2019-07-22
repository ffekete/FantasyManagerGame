package com.mygdx.game.resolver;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class ModdablePathResolver implements PathResolver {

    public Texture resolve(String path) {

        FileHandle fileHandle = Gdx.files.internal("../mod/" + path);

        System.out.println(fileHandle.path());

        if (fileHandle.exists() && !fileHandle.isDirectory()) {
            return new Texture(fileHandle);
        }

        fileHandle = Gdx.files.internal(path);
        if (fileHandle.exists() && !fileHandle.isDirectory()) {
            return new Texture(Gdx.files.internal(path));
        }

        return null;
    }

}