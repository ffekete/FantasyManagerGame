package com.mygdx.game.logic.activity;

import java.util.PriorityQueue;

public class ActivityStack {

    private PriorityQueue<Activity> activities;

    public ActivityStack(PriorityQueue<Activity> activities) {
        this.activities = activities;
    }

    public void suspendAll() {
        activities.forEach(Activity::suspend);
    }

    public boolean contains(Class clazz) {
        return activities.stream().filter(activity -> activity.getClass().isAssignableFrom(clazz)).count() == 1;
    }

    public boolean isEmpty() {
        return activities.isEmpty();
    }

    public void performNext() {
        if (activities.isEmpty()) {
            return;
        }
        Activity activity = activities.peek();
        if(activity.isCancellable()) {
            activity.cancel();
            activities.remove(activity);
        } else {
            if (activity.isFirstRun()) {
                activity.init();
            }
            if (activity.isSuspended()) {
                activity.resume();
            }

            activity.countDown();

            if (activity.isDone()) {
                activity.clear();
                activities.remove(activity);
                System.out.println("activity cleared + " + activities.size());
            } else {
                if (activity.isTriggered()) {
                    activity.update();
                }
            }
        }
    }

    public void add(Activity activity) {
        System.out.println("activity added");
        this.activities.offer(activity);
    }
}
