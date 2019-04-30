package com.mygdx.game.actor.factory;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.animation.AnimationBuilder;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.AnimationRegistry;

public class ActorFactory {

    public static final ActorFactory INSTANCE = new ActorFactory();

    public Actor create(Class<? extends Actor> clazz, Map2D map, ActorPlacementStrategy placementStrategy) {
        Actor actor = null;
        try {
            actor = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if(actor != null) {
            ActorRegistry.INSTANCE.add(map, actor);
            actor.setCurrentMap(map);
            placementStrategy.place(actor, map);
            AnimationRegistry.INSTANCE.add(actor, AnimationBuilder.INSTANCE.build(actor));
        }
        return actor;
    }

}
