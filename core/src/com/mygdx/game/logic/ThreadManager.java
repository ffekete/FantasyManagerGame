package com.mygdx.game.logic;

import com.mygdx.game.Config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {

    public static final ThreadManager INSTANCE = new ThreadManager();

    private final ExecutorService executorService = Executors.newFixedThreadPool(Config.Engine.NUMBER_OF_THREADS);

    public ExecutorService getExecutor() {
        return this.executorService;
    }

    private ThreadManager() {
    }
}
