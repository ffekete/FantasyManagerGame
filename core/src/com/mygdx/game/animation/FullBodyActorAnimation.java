package com.mygdx.game.animation;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.single.*;
import com.mygdx.game.registry.AnimationRegistry;
import com.mygdx.game.registry.RendererToolsRegistry;
import com.mygdx.game.registry.TextureRegistry;

public class FullBodyActorAnimation implements ActorAnimation {

    private TextureRegistry textureRegistry = TextureRegistry.INSTANCE;

    private float phase = 0;

    private final float offset = (1f - Config.Engine.ACTOR_HEIGHT) / 2f;

    @Override
    public void drawKeyFrame(SpriteBatch spriteBatch, float x, float y, float scale, Direction direction, Activity activity, Actor actor) {
        phase = (phase + 0.1f) % 3f;

        int row = getRow(activity);

        // body
        spriteBatch.setColor(Color.valueOf(actor.getAppearance().getBodyColor()));
        spriteBatch.draw(getBodyTexture(actor), x + offset,y+ offset, 0, 0, 1, 1, scale, scale, 0, (int)phase * 32,  32 * row, 32,32, getFlip(direction), false);

        // eyes
        spriteBatch.setColor(Color.valueOf(actor.getAppearance().getEyesColor()));
        spriteBatch.draw(getEyesTexture(actor), x + offset,y+ offset, 0, 0, 1, 1, scale, scale, 0, (int)phase * 32,  32 * row, 32,32, getFlip(direction), false);

        // hair
        spriteBatch.setColor(Color.valueOf(actor.getAppearance().getHairColor()));
        spriteBatch.draw(getHairTexture(actor), x + offset,y+ offset, 0, 0, 1, 1, scale, scale, 0, (int)phase * 32,  32 * row, 32,32, getFlip(direction), false);

        // armor
        spriteBatch.draw(textureRegistry.getFor(actor.getWornArmor().getClass()), x + offset,y+ offset, 0, 0, 1, 1, scale, scale, 0f, (int)phase * 32, 32 * row, 32, 32, getFlip(direction), false);


        spriteBatch.setColor(Color.WHITE);

    }

    private boolean getFlip(Direction direction) {
        return direction.equals(Direction.LEFT) || direction.equals(Direction.UP) ? true : false;
    }

    private Texture getBodyTexture(Actor actor) {
        return textureRegistry.getBody(actor.getBodyType().getArchetype(), "body", actor.getAppearance().getBodyIndex());
    }

    private Texture getHairTexture(Actor actor) {
        return textureRegistry.getBody(actor.getBodyType().getArchetype(), "hair", actor.getAppearance().getHairIndex());
    }

    private Texture getEyesTexture(Actor actor) {
        return textureRegistry.getBody(actor.getBodyType().getArchetype(), "eyes", actor.getAppearance().getEyesIndex());
    }

    private int getRow(Activity activity) {
        if(WaitActivity.class.isAssignableFrom(activity.getCurrentClass()) ||
                IdleActivity.class.isAssignableFrom(activity.getCurrentClass()) ||
                TimedIdleActivity.class.isAssignableFrom(activity.getCurrentClass())) {
            return AnimationPhase.WALK.column;
        }

        if(ConsumeHealingPotion.class.isAssignableFrom(activity.getCurrentClass())) {
            return AnimationPhase.WALK.column;
        }

        if(PreCalculatedMovementActivity.class.isAssignableFrom(activity.getCurrentClass()) ||
        MovementActivity.class.isAssignableFrom(activity.getCurrentClass()) ||
                ExplorationActivity.class.isAssignableFrom(activity.getCurrentClass())
        ) {
            return AnimationPhase.WALK.column;
        }

        if(SleepActivity.class.isAssignableFrom(activity.getCurrentClass())) {
            return AnimationPhase.SLEEP_IN_BED.column;
        }

        if(SleepAtCampfireActivity.class.isAssignableFrom(activity.getCurrentClass()) || SleepOutsideActivity.class.isAssignableFrom(activity.getCurrentClass())) {
            return AnimationPhase.SLEEP_OUTSIDE.column;
        }

        return AnimationPhase.ATTACK.column; // attack
    }

    private enum AnimationPhase {
        WALK(0),
        ATTACK(1),
        SLEEP_IN_BED(2),
        SLEEP_OUTSIDE(3);

        private int column;

        AnimationPhase(int column) {
            this.column = column;
        }
    }
}
