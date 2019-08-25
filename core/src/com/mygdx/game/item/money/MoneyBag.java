package com.mygdx.game.item.money;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.AbstractItem;

public class MoneyBag extends AbstractItem implements MoneyContainer {

    private int amount;

    public MoneyBag() {
    }

    @Override
    public String getDescription() {
        return "Bag containing some money";
    }

    @Override
    public String getName() {
        return "Moneybag";
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void pickedUp(Actor actor) {
        actor.addMoney(this.amount);
    }
}
