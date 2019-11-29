package com.mygdx.game.actor.wildlife;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.actor.MeleeActor;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.ItemFactory;
import com.mygdx.game.item.component.WolfPelt;
import com.mygdx.game.item.factory.Placement;

import java.util.Arrays;
import java.util.List;

import static com.mygdx.game.faction.Alignment.NEUTRAL;

public class Bear extends AbstractActor implements MeleeActor {

    public Bear() {
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
        return "Bear";
    }

    @Override
    public List<Item> drop() {
        return Arrays.asList();
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.Bear;
    }

    @Override
    public int getMana() {
        return 0;
    }
}
