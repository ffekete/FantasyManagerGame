package com.mygdx.game.item.food;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.AbstractItem;

public class RabbitMeat extends AbstractItem implements Food {

    @Override
    public int getNutritionAmount() {
        return Config.Item.RABBIT_MEAT_NUTRITION;
    }

    @Override
    public void consume(Actor actor) {
        actor.decreaseHunger(getNutritionAmount());
    }

    @Override
    public String getDescription() {
        return "Raw rabbit meat.";
    }

    @Override
    public String getName() {
        return "Rabbit meat";
    }

    @Override
    public int getPrice() {
        return 2;
    }
}
