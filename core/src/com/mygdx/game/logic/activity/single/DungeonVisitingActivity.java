package com.mygdx.game.logic.activity.single;

import com.mygdx.game.logic.activity.Activity;

public class DungeonVisitingActivity implements Activity {
    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public void update() {

    }

    @Override
    public void init() {

    }

    @Override
    public void cancel() {

    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean isFirstRun() {
        return false;
    }

    @Override
    public void suspend() {

    }

    @Override
    public void resume() {

    }

    @Override
    public boolean isSuspended() {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean isCancellable() {
        return false;
    }

    @Override
    public Class getCurrentClass() {
        return null;
    }

    @Override
    public Activity getCurrentActivity() {
        return null;
    }

    @Override
    public Class<? extends Activity> getMainClass() {
        return null;
    }

    @Override
    public void countDown() {

    }

    @Override
    public boolean isTriggered() {
        return false;
    }

    @Override
    public int compareTo(Activity o) {
        return 0;
    }
}
