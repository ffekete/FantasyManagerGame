package com.mygdx.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.armor.BlackPlateMail;
import com.mygdx.game.item.shield.Shield;

public class AnimationBuilder {

    public static final AnimationBuilder INSTANCE = new AnimationBuilder();

    public ActorAnimation build(Actor actor) {

        if(BlackPlateMail.class.isAssignableFrom(actor.getWornArmor().getClass())) {
            return ActorAnimation.builder()
                    .withHead(new Texture(Gdx.files.internal("black_plate_head.png")))
                    .withTorso(new Texture(Gdx.files.internal("black_plate_torso.png")))
                    .withLegs(new Texture(Gdx.files.internal("black_plate_leg.png")))
                    .withArms(new Texture(Gdx.files.internal("black_plate_hand.png")))
                    .withLeftHandItem(getLeftHandItem(actor))
                    .build();
        }

        // todo return dummy here
        return null;

        // WARRIOR
        /*
        else if(Warrior.class.isAssignableFrom(actor.getClass())) {
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
        }*/
    }

    private Texture getLeftHandItem(Actor actor) {
        return actor.getLeftHandItem() != null && Shield.class.isAssignableFrom(actor.getLeftHandItem().getClass()) ? new Texture(Gdx.files.internal("black_shield_idle.png")) : null;
    }

}
