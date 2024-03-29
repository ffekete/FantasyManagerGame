package com.mygdx.game.logic.activity;

import java.util.ArrayList;
import java.util.List;

public abstract class CompoundActivity implements Activity {

    private int currentActivity = 0;
    private List<Activity> activities;
    private int priority;
    private boolean firstRun = true;
    private boolean suspended = false;
    private Class<? extends Activity> mainClazz;

    public CompoundActivity(int priority, Class<? extends Activity> mainClazz) {
        this.activities = new ArrayList<>();
        this.priority = priority;
        this.mainClazz = mainClazz;
    }

    @Override
    public boolean isDone() {
        // last Item is done
        return activities.size() -1 == currentActivity && activities.get(currentActivity).isDone();
    }

    @Override
    public void update() {
        activities.get(currentActivity).update();
        if (activities.get(currentActivity).isDone()) {
            if (activities.size() - 1 == currentActivity) {
                // this was the last activity, nothing else to do here
            } else {
                activities.get(currentActivity).clear();
                currentActivity++;
                activities.get(currentActivity).init();
            }
        }
    }

    public CompoundActivity add(Activity activity) {
        this.activities.add(activity);
        return this;
    }

    @Override
    public void init() {
        activities.get(0).init();
        firstRun = false;
    }

    @Override
    public void cancel() {
        for(int i = currentActivity; i < activities.size(); i++) {
            activities.get(currentActivity).cancel();
        }
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public boolean isFirstRun() {
        return firstRun;
    }

    @Override
    public void suspend() {
        suspended = true;
        for(int i = 0; i < activities.size(); i++) {
            activities.get(i).suspend();
        }
    }

    @Override
    public void resume() {
        suspended = false;
        for(int i = 0; i < activities.size(); i++) {
            activities.get(i).resume();
        }
        currentActivity = 0;

    }

    @Override
    public boolean isSuspended() {
        return suspended;
    }

    @Override
    public void clear() {
        activities.get(activities.size() -1).clear();
    }

    @Override
    public boolean isCancellable() {
        for(int i = 0; i < activities.size(); i++) {
            if(activities.get(currentActivity).isCancellable())
                return true;
        }
        return false;
    }

    @Override
    public void countDown() {
        activities.get(currentActivity).countDown();
    }

    @Override
    public boolean isTriggered() {
        return activities.get(currentActivity).isTriggered();
    }

    @Override
    public int compareTo(Activity o) {
        return Integer.compare(priority, o.getPriority());
    }

    @Override
    public Class getCurrentClass() {
        return activities.get(currentActivity).getCurrentClass();
    }

    @Override
    public Class<? extends Activity> getMainClass() {
        return mainClazz;
    }

    @Override
    public Activity getCurrentActivity() {
        return activities.get(currentActivity);
    }
}
