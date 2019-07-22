package com.mygdx.game.resolver;

public interface PathResolver<T> {
    T resolve(String path);
}