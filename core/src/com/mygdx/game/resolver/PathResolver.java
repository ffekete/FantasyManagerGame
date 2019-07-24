package com.mygdx.game.resolver;

import java.util.Optional;

public interface PathResolver<T> {
    Optional<T> resolve(String path);
}