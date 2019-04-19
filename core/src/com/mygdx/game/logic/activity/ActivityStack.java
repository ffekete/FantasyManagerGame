package com.mygdx.game.logic.activity;

import java.util.PriorityQueue;

public class ActivityStack {

    private PriorityQueue<Activity> activities;

    public ActivityStack(PriorityQueue<Activity> activities) {
        this.activities = activities;
    }

    public boolean isEmpty() {
        return activities.isEmpty();
    }

    public void performNext() {
        if (activities.isEmpty()) {
            return;
        }
        Activity activity = activities.peek();
        if (activity.isFirstRun()) {
            activity.init();
        }
        activity.countDown();

        if (activity.isDone()) {
            activities.remove(activity);
            System.out.println("activity cleared + " + activities.size());
        } else {
            if (activity.isTriggered()) {
                activity.update();
            }
        }

    }

    public void add(Activity activity) {
        System.out.println("activity added");
        this.activities.offer(activity);
    }
}
