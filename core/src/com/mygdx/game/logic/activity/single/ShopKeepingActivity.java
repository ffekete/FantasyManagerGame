package com.mygdx.game.logic.activity.single;

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
import com.mygdx.game.object.furniture.ShopkeepersDesk;
import com.mygdx.game.registry.ActionRegistry;
import com.mygdx.game.registry.ItemRegistry;
import com.mygdx.game.registry.TextureRegistry;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ShopKeepingActivity implements Activity {

    private final ActionRegistry actionRegistry = ActionRegistry.INSTANCE;

    private Actor actor;
    private ShopkeepersDesk object;
    private int counter = 0;
    private boolean firstRun = true;
    private boolean suspended = false;

    public ShopKeepingActivity(Actor actor, ShopkeepersDesk object) {
        this.actor = actor;
        this.object = object;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public void update() {

    }

    @Override
    public void init() {

    }

    @Override
    public void cancel() {

    }

    @Override
    public int getPriority() {
        return Config.ShopKeeperActivity.SHOPKEEPING_PRIORITY;
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
}
