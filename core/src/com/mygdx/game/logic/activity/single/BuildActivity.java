package com.mygdx.game.logic.activity.single;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.builder.BuildingBlock;
import com.mygdx.game.item.buildertool.Hammer;
import com.mygdx.game.logic.action.Action;
import com.mygdx.game.logic.action.HammerSwingAction;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.object.factory.ObjectFactory;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.registry.ActionRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.ObjectRegistry;
import com.mygdx.game.registry.TextureRegistry;

public class BuildActivity implements Activity {

    private final ObjectRegistry objectRegistry = ObjectRegistry.INSTANCE;
    private final ActionRegistry actionRegistry = ActionRegistry.INSTANCE;

    private Actor actor;
    private BuildingBlock object;
    private int counter;
    private boolean firstRun = true;
    Action action;

    public BuildActivity(Actor actor, BuildingBlock object) {
        this.actor = actor;
        this.object= object;
    }

    @Override
    public boolean isDone() {
        return object.isFinished();
    }

    @Override
    public void update() {
        object.addProgress(15f);
        action = new HammerSwingAction(actor.getX(), actor.getY(), TextureRegistry.INSTANCE.getFor(Hammer.class), actor);
        actionRegistry.add(actor.getCurrentMap(), action);
    }

    @Override
    public void init() {
        counter = 0;
        firstRun = false;

    }

    @Override
    public void cancel() {

    }

    @Override
    public int getPriority() {
        return Config.Activity.BUILD_PRIORITY;
    }

    @Override
    public boolean isFirstRun() {
        return firstRun;
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
        objectRegistry.remove(actor.getCurrentMap(), object);
        actor.getCurrentMap().setObstacle((int)object.getX(), (int)object.getY(), false);
        ObjectFactory.create(object.finish(), actor.getCurrentMap(), ObjectPlacement.FIXED.X((int)object.getX()).Y((int)object.getY()));
    }

    @Override
    public boolean isCancellable() {
        return false;
    }

    @Override
    public Class getCurrentClass() {
        return this.getClass();
    }

    @Override
    public void countDown() {
        counter = (counter + 1) % 60;
    }

    @Override
    public boolean isTriggered() {
        return counter == 59;
    }

    @Override
    public int compareTo(Activity o) {
        return Integer.compare(getPriority(), o.getPriority());
    }

    @Override
    public Activity getCurrentActivity() {
        return this;
    }

}
