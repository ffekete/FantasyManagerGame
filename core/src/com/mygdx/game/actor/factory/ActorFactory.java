package com.mygdx.game.actor.factory;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.component.attribute.AttributePopulator;
import com.mygdx.game.actor.component.skill.SkillPopulator;
import com.mygdx.game.actor.worker.Worker;
import com.mygdx.game.animation.FullBodyActorAnimation;
import com.mygdx.game.map.Map2D;
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
            actor.setCurrentMap(map);
            placementStrategy.place(actor, map);
            AnimationRegistry.INSTANCE.add(actor, new FullBodyActorAnimation());

            if(!Worker.class.isAssignableFrom(clazz)) {
                SkillPopulator.WeaponSkillPopulatorStrategy.RANDOM.populate(actor);
                SkillPopulator.MagicSkillPopulatorStrategy.RANDOM.populate(actor);
                AttributePopulator.ClassSpecificAttrbutePopulator.populate(actor);
            } else {
                // todo worker skill and attributes comes here
            }

            actor.setHp(actor.getMaxHp());
            actor.setMana(actor.getMaxMana());
        }
        return actor;
    }

}
