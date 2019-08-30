package com.mygdx.game.actor.wildlife;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.actor.MeleeActor;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.ItemFactory;
import com.mygdx.game.item.component.WolfPelt;
import com.mygdx.game.item.factory.Placement;

import java.util.Arrays;
import java.util.List;

import static com.mygdx.game.faction.Alignment.NEUTRAL;

public class Wolf extends AbstractActor implements MeleeActor {

    public Wolf() {
        this.setAlignment(NEUTRAL);
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
    public String getActorClass() {
        return "Wolf";
    }

    @Override
    public List<Item> drop() {
        return Arrays.asList(ItemFactory.INSTANCE.create(WolfPelt.class, this.getCurrentMap(), Placement.FIXED.X(getX()).Y(getY())));
    }

    @Override
    public int getMana() {
        return 0;
    }
}
