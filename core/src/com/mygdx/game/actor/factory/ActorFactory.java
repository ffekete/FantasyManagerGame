package com.mygdx.game.actor.factory;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Appearance;
import com.mygdx.game.actor.Gender;
import com.mygdx.game.actor.component.attribute.AttributePopulator;
import com.mygdx.game.actor.component.skill.SkillPopulator;
import com.mygdx.game.actor.worker.Worker;
import com.mygdx.game.animation.FullAnimationSet;
import com.mygdx.game.animation.FullBodyActorAnimation;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.AnimationRegistry;

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
        if (actor != null) {

            actor.setAppearance(getAppearance(actor, actor.getBodyType().getArchetype()));

            actor.setCurrentMap(map);
            placementStrategy.place(actor, map);
            AnimationRegistry.INSTANCE.add(actor, new FullBodyActorAnimation());

            if (!Worker.class.isAssignableFrom(clazz)) {
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

    private Appearance getAppearance(Actor actor, String archeType) {
        Appearance appearance = new Appearance();

        int genderIndex = new Random().nextInt(2);
        Gender gender = genderIndex == 0 ? Gender.Male : Gender.Female;
        actor.setGender(gender);

        setRandomBody(archeType, appearance, gender);
        setRandomEyes(archeType, appearance, gender);
        setRandomHair(archeType, appearance, gender);
        setRandomBeard(archeType, appearance, gender);
        return appearance;
    }

    private void setRandomEyes(String archeType, Appearance appearance, Gender gender) {

        int index;
        appearance.setEyesIndex(new Random().nextInt(animationSet.getAnimationSets().stream().filter(racialAnimationSet -> racialAnimationSet.getType().equals(archeType)).findFirst().get().getRacialAnimations().get(gender).getEyes().getParts().size()));

        index = new Random().nextInt(animationSet.getAnimationSets().stream().filter(racialAnimationSet -> racialAnimationSet.getType().equals(archeType)).findFirst().get().getRacialAnimations().get(gender).getEyes().getColors().size());
        appearance.setEyesColor(animationSet.getAnimationSets().stream().filter(racialAnimationSet -> racialAnimationSet.getType().equals(archeType)).findFirst().get().getRacialAnimations().get(gender).getEyes().getColors().get(index));
    }

    private void setRandomHair(String archeType, Appearance appearance, Gender gender) {
        int index;
        appearance.setHairIndex(new Random().nextInt(animationSet.getAnimationSets().stream().filter(racialAnimationSet -> racialAnimationSet.getType().equals(archeType)).findFirst().get().getRacialAnimations().get(gender).getHair().getParts().size()));

        index = new Random().nextInt(animationSet.getAnimationSets().stream().filter(racialAnimationSet -> racialAnimationSet.getType().equals(archeType)).findFirst().get().getRacialAnimations().get(gender).getHair().getColors().size());
        appearance.setHairColor(animationSet.getAnimationSets().stream().filter(racialAnimationSet -> racialAnimationSet.getType().equals(archeType)).findFirst().get().getRacialAnimations().get(gender).getHair().getColors().get(index));
    }

    private void setRandomBody(String archeType, Appearance appearance, Gender gender) {
        appearance.setBodyIndex(new Random().nextInt(animationSet.getAnimationSets().stream().filter(racialAnimationSet -> racialAnimationSet.getType().equals(archeType)).findFirst().get().getRacialAnimations().get(gender).getBody().getParts().size()));

        int index = new Random().nextInt(animationSet.getAnimationSets().stream().filter(racialAnimationSet -> racialAnimationSet.getType().equals(archeType)).findFirst().get().getRacialAnimations().get(gender).getBody().getColors().size());
        appearance.setBodyColor(animationSet.getAnimationSets().stream().filter(racialAnimationSet -> racialAnimationSet.getType().equals(archeType)).findFirst().get().getRacialAnimations().get(gender).getBody().getColors().get(index));
    }

    private void setRandomBeard(String archeType, Appearance appearance, Gender gender) {
        if (animationSet.getAnimationSets().stream().anyMatch(racialAnimationSet -> racialAnimationSet.getType().equals(archeType)) &&
                animationSet.getAnimationSets().stream().filter(racialAnimationSet -> racialAnimationSet.getType().equals(archeType)).findFirst().get().getRacialAnimations().get(gender).getBeard() != null &&
                !animationSet.getAnimationSets().stream().filter(racialAnimationSet -> racialAnimationSet.getType().equals(archeType)).findFirst().get().getRacialAnimations().get(gender).getBeard().getParts().isEmpty()
        ) {

            if(new Random().nextInt(5) == 0)
                appearance.setBeardIndex(new Random().nextInt(animationSet.getAnimationSets().stream().filter(racialAnimationSet -> racialAnimationSet.getType().equals(archeType)).findFirst().get().getRacialAnimations().get(gender).getBeard().getParts().size()));
        }
    }

}
