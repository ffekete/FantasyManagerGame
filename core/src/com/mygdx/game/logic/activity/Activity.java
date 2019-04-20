package com.mygdx.game.logic.activity;

public interface Activity extends CooldownActivity, Comparable<Activity> {

    boolean isDone();
    void update();
    void init();
    void cancel();
    int getPriority();
    boolean isFirstRun();
    void suspend();
    void resume();
    boolean isSuspended();
    void clear();
    boolean isCancellable();
}
