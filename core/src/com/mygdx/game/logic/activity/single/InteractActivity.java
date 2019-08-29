package com.mygdx.game.logic.activity.single;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.object.InteractiveObject;
import com.mygdx.game.logic.activity.Activity;

public class InteractActivity implements Activity {

    private Actor actor;
    private InteractiveObject object;
    private boolean suspended = false;

    public InteractActivity(Actor actor, InteractiveObject object) {
        this.actor = actor;
        this.object= object;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public void update() {

    }

    @Override
    public void init() {

    }

    @Override
    public void cancel() {
        actor.getActivityStack().reset();
    }

    @Override
    public int getPriority() {
        return Config.Activity.INTERACT_PRIORITY;
    }

    @Override
    public boolean isFirstRun() {
        return false;
    }

    @Override
    public void suspend() {
        suspended = true;
    }

    @Override
    public void resume() {
        suspended = false;
    }

    @Override
    public boolean isSuspended() {
        return suspended;
    }

    @Override
    public void clear() {
        if(object.canInteract(actor)) {
            object.onInteract(actor);
        }
    }

    @Override
    public boolean isCancellable() {
        return !object.canInteract(actor);
    }

    @Override
    public Class getCurrentClass() {
        return this.getClass();
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
        return Integer.compare(getPriority(), o.getPriority());
    }

    @Override
    public Activity getCurrentActivity() {
        return this;
    }

    @Override
    public Class<? extends Activity> getMainClass() {
        return this.getClass();
    }

}
