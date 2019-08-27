package com.mygdx.game.map.worldmap;

import com.google.common.collect.ImmutableList;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.factory.ActorFactory;
import com.mygdx.game.actor.factory.Placement;
import com.mygdx.game.actor.wildlife.Rabbit;
import com.mygdx.game.actor.wildlife.Wolf;

import java.util.List;
import java.util.Random;

public class WildlifeDistributor {

    public static final WildlifeDistributor INSTANCE = new WildlifeDistributor();

    private List<Class<? extends Actor>> predators = ImmutableList.<Class<? extends Actor>>builder()
            .add(Wolf.class)
            .build();

    private List<Class<? extends Actor>> preys = ImmutableList.<Class<? extends Actor>>builder()
            .add(Rabbit.class)
            .build();

    public void populate(WorldMap worldMap) {

        for(int i = 0; i < 30; i++) {
            int index = new Random().nextInt(preys.size());
            ActorFactory.INSTANCE.create(preys.get(index), worldMap, Placement.RANDOM);
        }

        for(int i = 0; i < 10; i++) {
            int index = new Random().nextInt(predators.size());
            ActorFactory.INSTANCE.create(preys.get(index), worldMap, Placement.RANDOM);
        }
    }

}
