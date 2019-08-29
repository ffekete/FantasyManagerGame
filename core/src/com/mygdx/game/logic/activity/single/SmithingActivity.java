package com.mygdx.game.logic.activity.single;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Craftable;
import com.mygdx.game.item.buildertool.Hammer;
import com.mygdx.game.item.category.Tier1;
import com.mygdx.game.logic.action.Action;
import com.mygdx.game.logic.action.HammerSwingAction;
import com.mygdx.game.logic.action.SparksAction;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.object.CraftingObject;
import com.mygdx.game.registry.ActionRegistry;
import com.mygdx.game.registry.ItemRegistry;
import com.mygdx.game.registry.TextureRegistry;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SmithingActivity implements Activity {

    private final ActionRegistry actionRegistry = ActionRegistry.INSTANCE;

    private Actor actor;
    private CraftingObject object;
    private int counter = 0;
    private Action action;
    private boolean firstRun = true;
    private int interactedCounter = 1;
    private boolean suspended = false;

    private Sound hitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/hit/hit07.mp3"));

    public SmithingActivity(Actor actor, CraftingObject object) {
        this.actor = actor;
        this.object = object;
    }

    @Override
    public boolean isDone() {
        return object.isFinished();
    }

    @Override
    public void update() {
        interactedCounter++;
        object.addProgress(0.1f);
        action = new HammerSwingAction(actor.getX(), actor.getY(), TextureRegistry.INSTANCE.getFor(Hammer.class), actor);
        actionRegistry.add(actor.getCurrentMap(), action);

        actionRegistry.add(actor.getCurrentMap(), new SparksAction((int)object.getX(), (int)object.getY()));
    }

    @Override
    public void init() {
        object.onInteract(actor);
        List<Class<Craftable>> craftables = ItemRegistry.INSTANCE.getFor(Tier1.class).stream().filter(item -> Craftable.class.isAssignableFrom(item)).map(item -> (Class<Craftable>) item).collect(Collectors.toList());
        object.start(craftables.get(new Random().nextInt(craftables.size())));
        firstRun = false;
    }

    @Override
    public void cancel() {
        object.cancel();
    }

    @Override
    public int getPriority() {
        return Config.SmithActivity.SMITHING_PRIORITY;
    }

    @Override
    public boolean isFirstRun() {
        return firstRun;
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
        object.finish();

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
        counter = (counter + 1) % actor.getAttackSpeed();
    }

    @Override
    public boolean isTriggered() {
        return counter == 1;
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

    public int getInteractedCounter() {
        return interactedCounter;
    }

    public void increaseInteractedCounter() {
        interactedCounter++;
    }
}
