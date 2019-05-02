package com.mygdx.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.armor.BlackPlateMail;
import com.mygdx.game.item.shield.Shield;
import com.mygdx.game.item.weapon.Weapon;

public class AnimationBuilder {

    public static final AnimationBuilder INSTANCE = new AnimationBuilder();

    public ActorAnimation build(Actor actor) {

        if(BlackPlateMail.class.isAssignableFrom(actor.getWornArmor().getClass())) {
            return BodyPartsBasedActorAnimation.builder()
                    .withHead(new Texture(Gdx.files.internal("black_plate_head.png")))
                    .withTorso(new Texture(Gdx.files.internal("black_plate_torso.png")))
                    .withLegs(new Texture(Gdx.files.internal("black_plate_legs.png")))
                    .withArms(new Texture(Gdx.files.internal("black_plate_arms.png")))
                    .withLeftHandItem(getLeftHandItem(actor))
                    .withRightHandItem(getRightHandItem(actor))
                    .build();
        }

        // todo return dummy here
        return null;
    }

    private Texture getLeftHandItem(Actor actor) {
        if(actor.getLeftHandItem() != null && Shield.class.isAssignableFrom(actor.getLeftHandItem().getClass()))
            return new Texture(Gdx.files.internal("black_plate_shield.png"));

        if(actor.getLeftHandItem() != null && Weapon.class.isAssignableFrom(actor.getLeftHandItem().getClass()))
            return new Texture(Gdx.files.internal("black_plate_right_sword.png"));

        return null;
    }

    private Texture getRightHandItem(Actor actor) {
        if(actor.getRightHandItem() != null && Shield.class.isAssignableFrom(actor.getRightHandItem().getClass()))
            return new Texture(Gdx.files.internal("black_plate_shield.png"));

        if(actor.getRightHandItem() != null && Weapon.class.isAssignableFrom(actor.getRightHandItem().getClass()))
            return new Texture(Gdx.files.internal("black_plate_right_sword.png"));

        return null;
    }

}
