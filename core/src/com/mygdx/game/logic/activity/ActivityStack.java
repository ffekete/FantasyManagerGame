package com.mygdx.game.logic.activity;

import com.mygdx.game.actor.Actor;

import java.util.PriorityQueue;

public class ActivityStack {

    private PriorityQueue<Activity> activities;

    public ActivityStack(PriorityQueue<Activity> activities, Actor actor) {
        this.activities = activities;
        activities.add(new IdleActivity(actor));
    }

    public void suspendAll() {
        activities.forEach(Activity::suspend);
    }

    public boolean contains(Class clazz) {
        return activities.stream().filter(activity -> activity.getClass().isAssignableFrom(clazz)).count() == 1;
    }

    public int getSize() {
        return activities.size();
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
                System.out.println("activity cleared + " + activity);
            } else {
                if (activity.isTriggered()) {
                    activity.update();
                }
            }
        }
    }

    public Activity getCurrent() {
        if(activities.isEmpty()) {
            return null;
        }
        return activities.peek();
    }

    public void add(Activity activity) {
        System.out.println("activity added + " + activity.getClass());
        this.activities.offer(activity);
    }

    public boolean finishedCurrentPeriod() {
        if(activities.isEmpty()) {
            return false;
        }
        return activities.peek().isTriggered();
    }
}
