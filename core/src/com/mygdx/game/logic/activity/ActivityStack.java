package com.mygdx.game.logic.activity;

import java.util.PriorityQueue;

public class ActivityStack {

    private PriorityQueue<Activity> activities;

    public ActivityStack(PriorityQueue<Activity> activities) {
        this.activities = activities;
    }

    public void performNext() {
        if(activities.isEmpty()) {
            return;
        }
        Activity activity = activities.peek();
        if(activity.isFirstRun()) {
            activity.init();
        }
        if(activity.isDone()) {
            activities.remove(activity);
            System.out.println("activity cleared + " + activities.size());
        } else {
            activity.update();
        }
    }

    public void add(Activity activity) {
        System.out.println("activity added");
        this.activities.offer(activity);
    }
}
