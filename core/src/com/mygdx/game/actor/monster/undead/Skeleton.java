package com.mygdx.game.actor.monster.undead;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.actor.MeleeActor;
import com.mygdx.game.item.category.Tier1;

import static com.mygdx.game.faction.Alignment.ENEMY;

public class Skeleton extends AbstractActor implements MeleeActor {

    public Skeleton() {
        this.setAlignment(ENEMY);
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
    public int getHungerLevel() {
        return 0;
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
        return "Skeleton";
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.HumanoidSkeleton;
    }
}
