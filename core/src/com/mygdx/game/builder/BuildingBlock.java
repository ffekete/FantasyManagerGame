package com.mygdx.game.builder;

public interface BuildingBlock<T> {

    void addProgress(float percentage);
    T finish();
    boolean isFinished();

}
