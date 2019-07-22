package com.mygdx.game.utils;

import com.google.inject.Guice;
import com.mygdx.game.DIConfig;

public class Injector {

    public static final Injector INSTANCE = new Injector();

    private com.google.inject.Injector injector;

    private Injector() {
        injector = Guice.createInjector(new DIConfig());
    }

    com.google.inject.Injector getInjector() {
        return this.injector;
    }

    public static <T> T getInstance(Class<T> clazz) {
        return INSTANCE.getInjector().getInstance(clazz);
    }
}
