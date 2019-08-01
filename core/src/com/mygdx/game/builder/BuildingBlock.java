package com.mygdx.game.builder;

import com.mygdx.game.object.WorldObject;

public interface BuildingBlock<T> extends WorldObject {

    void addProgress(float percentage);
    Class<T> finish();
    boolean isFinished();
    float getProgress();
    float getX();
    float getY();
}
