package com.mygdx.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.item.shield.Shield;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.ExplorationActivity;
import com.mygdx.game.logic.activity.IdleActivity;
import com.mygdx.game.logic.activity.MovementActivity;
import com.mygdx.game.logic.activity.PickUpItemActivity;

import java.util.HashMap;
import java.util.Map;

public class AnimationBuilder {

    public static final AnimationBuilder INSTANCE = new AnimationBuilder();

    public Map<Class<? extends Activity>, Animation> buildFor(Actor actor) {
        return getClassAnimationMap(actor);
    }

    private Map<Class<? extends Activity>, Animation> getClassAnimationMap(Actor actor) {
        Map<Class<? extends Activity>, Animation> map = new HashMap<>();

        // WARRIOR
        if(Warrior.class.isAssignableFrom(actor.getClass())) {
            map.put(MovementActivity.class, ActorAnimation.builder()
                    .withHead(new Texture(Gdx.files.internal("head_idle.png")))
                    .withTorso(new Texture(Gdx.files.internal("torso_idle.png")))
                    .withLegs(new Texture(Gdx.files.internal("leg_moving.png")))
                    .withArms(new Texture(Gdx.files.internal("hand_idle.png")))
                    .withLeftHandItem(getLeftHandItem(actor))
                    .build());

            map.put(IdleActivity.class, ActorAnimation.builder()
                    .withHead(new Texture(Gdx.files.internal("head_idle.png")))
                    .withTorso(new Texture(Gdx.files.internal("torso_idle.png")))
                    .withLegs(new Texture(Gdx.files.internal("leg_idle.png")))
                    .withArms(new Texture(Gdx.files.internal("hand_idle.png")))
                    .withLeftHandItem(getLeftHandItem(actor))
                    .build());

            map.put(PickUpItemActivity.class, ActorAnimation.builder()
                    .withHead(new Texture(Gdx.files.internal("head_idle.png")))
                    .withTorso(new Texture(Gdx.files.internal("torso_idle.png")))
                    .withLegs(new Texture(Gdx.files.internal("leg_idle.png")))
                    .withArms(new Texture(Gdx.files.internal("hand_idle.png")))
                    .withLeftHandItem(getLeftHandItem(actor))
                    .build());
        }

        return map;
    }

    private Texture getLeftHandItem(Actor actor) {
        return actor.getLeftHandItem() != null && Shield.class.isAssignableFrom(actor.getLeftHandItem().getClass()) ? new Texture(Gdx.files.internal("shield_idle.png")) : null;
    }

}
