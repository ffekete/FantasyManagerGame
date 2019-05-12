package com.mygdx.game.logic.activity.stack;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.single.IdleActivity;

import java.util.PriorityQueue;

public class ActivityStack {

    private PriorityQueue<Activity> activities;

    public ActivityStack(PriorityQueue<Activity> activities, Actor actor) {
        this.activities = activities;
        activities.add(new IdleActivity(actor));
    }

    public void debug() {
        activities.forEach(activity -> {
            System.out.println(activity.getClass());
        });
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

        if (activity.isTriggered()) {
            activity.update();
            if (activity.isDone()) {
                activity.clear();
                System.out.println(activities.remove(activity));
                System.out.println("activity cleared + " + activity);
            }
        }
        if (activity.isCancellable()) {
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
        }
    }

    public Activity getCurrent() {
        if (activities.isEmpty()) {
            return null;
        }
        return activities.peek();
    }

    public void add(Activity activity) {
        System.out.println("activity added + " + activity.getClass());
        this.activities.offer(activity);
    }

    public boolean finishedCurrentPeriod() {
        if (activities.isEmpty() || (activities.size() == 1 && IdleActivity.class.isAssignableFrom(activities.peek().getCurrentClass()))) {
            return true;
        }
        return activities.peek().isTriggered();
    }
}
