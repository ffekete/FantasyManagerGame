package com.mygdx.game.logic.activity.single;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.builder.BuildingBlock;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.buildertool.Hammer;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.action.Action;
import com.mygdx.game.logic.action.HammerSwingAction;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.command.Command;
import com.mygdx.game.object.Cuttable;
import com.mygdx.game.object.Targetable;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.factory.ObjectFactory;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.registry.ActionRegistry;
import com.mygdx.game.registry.CommandRegistry;
import com.mygdx.game.registry.ObjectRegistry;
import com.mygdx.game.registry.TextureRegistry;
import org.hamcrest.core.CombinableMatcher;

public class ExecuteCutDownActivity implements Activity {

    private final ObjectRegistry objectRegistry = ObjectRegistry.INSTANCE;
    private final ActionRegistry actionRegistry = ActionRegistry.INSTANCE;

    private Actor actor;
    private Cuttable object;
    private Command command;
    private int counter;
    private boolean firstRun = true;
    Action action;

    public ExecuteCutDownActivity(Actor actor, Command<Cuttable> command) {
        this.actor = actor;
        this.object= command.getTarget();
        this.command = command;
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
        return Config.BuilderActivity.BUILD_PRIORITY;
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
        objectRegistry.remove(actor.getCurrentMap(), (WorldObject) object);

        //actor.getCurrentMap().setObstacle((int)object.getX(), (int)object.getY(), false);

        try {
            actor.getInventory().add((Item)object.finish(actor).getDeclaredConstructor(Point.class).newInstance(actor.getCoordinates()));

        } catch (Exception e) {
            // hmm
        }

        System.out.println(CommandRegistry.INSTANCE.getCommands().size());
        CommandRegistry.INSTANCE.getCommands().remove(command);
        System.out.println(CommandRegistry.INSTANCE.getCommands().size());

        //ObjectFactory.create(, actor.getCurrentMap(), ObjectPlacement.FIXED.X((int)((WorldObject)object).getX()).Y((int)((WorldObject)object).getY()));
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
