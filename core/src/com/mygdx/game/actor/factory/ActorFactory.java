package com.mygdx.game.actor.factory;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Appearance;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.actor.component.attribute.AttributePopulator;
import com.mygdx.game.actor.component.skill.SkillPopulator;
import com.mygdx.game.actor.worker.Worker;
import com.mygdx.game.animation.ArcheTypeAnimationSet;
import com.mygdx.game.animation.FullAnimationSet;
import com.mygdx.game.animation.FullBodyActorAnimation;
import com.mygdx.game.animation.JsonReader;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.AnimationRegistry;

import java.util.ArrayList;
import java.util.Random;

public class ActorFactory {

    public static final ActorFactory INSTANCE = new ActorFactory();

    FullAnimationSet animationSet = AnimationRegistry.INSTANCE.getAnimationSet();

    public Actor create(Class<? extends Actor> clazz, Map2D map, ActorPlacementStrategy placementStrategy) {
        Actor actor = null;
        try {
            actor = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if(actor != null) {

            actor.setAppearance(getAppearance(actor.getBodyType().getArchetype()));

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

    private Appearance getAppearance(String archeType) {
        Appearance appearance = new Appearance();

        appearance.setBodyIndex(new Random().nextInt(animationSet.getAnimationSets().stream().filter(archeTypeAnimationSet -> archeTypeAnimationSet.getType().equals(archeType)).findFirst().get().getBody().getParts().size()));

        int index = new Random().nextInt(animationSet.getAnimationSets().stream().filter(archeTypeAnimationSet -> archeTypeAnimationSet.getType().equals(archeType)).findFirst().get().getBody().getColors().size());
        appearance.setBodyColor(animationSet.getAnimationSets().stream().filter(archeTypeAnimationSet -> archeTypeAnimationSet.getType().equals(archeType)).findFirst().get().getBody().getColors().get(index));

        appearance.setEyesIndex(new Random().nextInt(animationSet.getAnimationSets().stream().filter(archeTypeAnimationSet -> archeTypeAnimationSet.getType().equals(archeType)).findFirst().get().getEyes().getParts().size()));

        index = new Random().nextInt(animationSet.getAnimationSets().stream().filter(archeTypeAnimationSet -> archeTypeAnimationSet.getType().equals(archeType)).findFirst().get().getEyes().getColors().size());
        appearance.setEyesColor(animationSet.getAnimationSets().stream().filter(archeTypeAnimationSet -> archeTypeAnimationSet.getType().equals(archeType)).findFirst().get().getEyes().getColors().get(index));

        appearance.setHairIndex(new Random().nextInt(animationSet.getAnimationSets().stream().filter(archeTypeAnimationSet -> archeTypeAnimationSet.getType().equals(archeType)).findFirst().get().getHair().getParts().size()));

        index = new Random().nextInt(animationSet.getAnimationSets().stream().filter(archeTypeAnimationSet -> archeTypeAnimationSet.getType().equals(archeType)).findFirst().get().getHair().getColors().size());
        appearance.setHairColor(animationSet.getAnimationSets().stream().filter(archeTypeAnimationSet -> archeTypeAnimationSet.getType().equals(archeType)).findFirst().get().getHair().getColors().get(index));

        return appearance;
    }

}
