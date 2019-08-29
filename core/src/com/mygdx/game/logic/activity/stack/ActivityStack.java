package com.mygdx.game.logic.activity.stack;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.single.IdleActivity;
import com.mygdx.game.logic.actor.ActorMovementHandler;

import java.util.PriorityQueue;

public class ActivityStack {

    private PriorityQueue<Activity> activities;
    private Actor actor;

    public ActivityStack(PriorityQueue<Activity> activities, Actor actor) {
        this.activities = activities;
        activities.add(new IdleActivity(actor));
        this.actor = actor;
    }

    public void suspendAll() {
        activities.forEach(Activity::suspend);
    }

    public boolean contains(Class clazz) {
        return activities.stream().filter(activity -> activity.getMainClass().equals(clazz)).count() >= 1;
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

        if (activity.isFirstRun()) {
            activity.init();
        }

        activity.countDown();

        if (activity.isTriggered()) {
            activity.update();
            if (activity.isDone()) {
                activity.clear();
                activities.remove(activity);
            }
        }
        else if (activity.isCancellable()) {
            activity.cancel();
            activities.remove(activity);
        } else {
            if (activity.isSuspended()) {
                activity.resume();
            }
        }
    }

    public void clear() {
        activities.clear();
    }

    public void reset() {
        activities.clear();
        activities.add(new IdleActivity(actor));
    }

    public Activity getCurrent() {
        if (activities.isEmpty()) {
            return null;
        }
        return activities.peek();
    }

    public void add(Activity activity) {
        //actor.getActivityStack().reset();
        ActorMovementHandler.INSTANCE.clearPath(actor);
        this.activities.offer(activity);
        if(this.activities.peek().equals(activity)) {
            long suspended = activities.parallelStream()
                    .peek(activity1 -> {
                        if(!activity1.equals(activity)) {
                            activity1.suspend();
                        }
                    }).count();
            System.out.println(actor.getName() + " suspended " + suspended + " activities by " + activity.getMainClass());
            for(Activity activity1 : activities) {
                System.out.println(" -> X " + activity1.getMainClass());
            }
        }

    }

    public boolean finishedCurrentPeriod() {
        if (activities.isEmpty() || (activities.size() == 1 && IdleActivity.class.isAssignableFrom(activities.peek().getCurrentClass()))) {
            return true;
        }
        return activities.peek().isTriggered();
    }
}
