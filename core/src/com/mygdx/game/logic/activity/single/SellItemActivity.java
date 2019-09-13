package com.mygdx.game.logic.activity.single;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Item;
import com.mygdx.game.logic.activity.Activity;

public class SellItemActivity implements Activity {

    private Actor seller;
    private Actor buyer;
    private Item toBeSold;
    private boolean suspended = false;

    public SellItemActivity(Actor seller, Actor buyer, Item toBeSold) {
        this.seller = seller;
        this.buyer = buyer;
        this.toBeSold = toBeSold;
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

    }

    @Override
    public int getPriority() {
        return Config.Activity.SELL_ITEM_PRIORITY;
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
        seller.getInventory().remove(toBeSold);
        buyer.getInventory().add(toBeSold);
        seller.addMoney(toBeSold.getPrice() / 3);
        buyer.removeMoney(toBeSold.getPrice() / 3);
    }

    @Override
    public boolean isCancellable() {
        return false;
    }

    @Override
    public Class getCurrentClass() {
        return SellItemActivity.class;
    }

    @Override
    public Activity getCurrentActivity() {
        return this;
    }

    @Override
    public Class<? extends Activity> getMainClass() {
        return SellItemActivity.class;
    }

    @Override
    public void countDown() {

    }

    @Override
    public boolean isTriggered() {
        return true;
    }

    @Override
    public int compareTo(Activity o) {
        return Integer.compare(getPriority(), o.getPriority());
    }
}
