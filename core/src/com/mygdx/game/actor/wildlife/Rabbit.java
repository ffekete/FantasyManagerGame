package com.mygdx.game.actor.wildlife;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.actor.Prey;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.ItemFactory;
import com.mygdx.game.item.factory.Placement;
import com.mygdx.game.item.food.RabbitMeat;

import java.util.Arrays;
import java.util.List;

import static com.mygdx.game.faction.Alignment.NEUTRAL;

public class Rabbit extends AbstractActor implements Prey {

    public Rabbit() {
        this.setAlignment(NEUTRAL);
    }

    @Override
    public boolean isHungry() {
        return false;
    }

    @Override
    public void increaseHunger(int amount) {

    }

    @Override
    public void decreaseHunger(int amount) {

    }

    @Override
    public boolean isSleepy() {
        return false;
    }

    @Override
    public void increaseSleepiness(int amount) {

    }

    @Override
    public void decreaseSleepiness(int amount) {

    }

    @Override
    public int getSleepinessLevel() {
        return 0;
    }

    @Override
    public int getHungerLevel() {
        return 0;
    }

    @Override
    public String getActorClass() {
        return "Rabbit";
    }

    @Override
    public List<Item> drop() {
        return Arrays.asList(ItemFactory.INSTANCE.create(RabbitMeat.class, this.getCurrentMap(), Placement.FIXED.X(getX()).Y(getY())));
    }

    @Override
    public int getMana() {
        return 0;
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.Humanoid;
    }
}
