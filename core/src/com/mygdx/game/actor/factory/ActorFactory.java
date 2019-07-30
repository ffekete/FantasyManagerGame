package com.mygdx.game.actor.factory;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.component.attribute.AttributePopulator;
import com.mygdx.game.actor.component.skill.SkillPopulator;
import com.mygdx.game.actor.worker.Worker;
import com.mygdx.game.animation.FullBodyActorAnimation;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.light.ActorLightSource;
import com.mygdx.game.object.light.LightSource;
import com.mygdx.game.object.light.LightSourceType;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.AnimationRegistry;
import com.mygdx.game.registry.LightSourceRegistry;

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
