package com.mygdx.game.item.food;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.category.Tier1;

public class Bread extends AbstractItem implements Food, Tier1 {

    @Override
    public void consume(Actor actor) {
        actor.decreaseHunger(Config.Item.BREAD_NUTRITION);
    }

    @Override
    public String getDescription() {
        return "Tasty bread.";
    }

    @Override
    public String getName() {
        return "Bread";
    }


    @Override
    public int getNutritionAmount() {
        return Config.Item.BREAD_NUTRITION;
    }

    @Override
    public int getPrice() {
        return 2;
    }
}
