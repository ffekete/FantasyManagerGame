package com.mygdx.game;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.mygdx.game.logic.action.SlowAction;
import com.mygdx.game.registry.TextureRegistry;

public class DIConfig extends AbstractModule {

    @Override
    protected void configure() {
        bind(SlowAction.class).in(Singleton.class);
        bind(TextureRegistry.class).toInstance(TextureRegistry.INSTANCE);
    }
}
