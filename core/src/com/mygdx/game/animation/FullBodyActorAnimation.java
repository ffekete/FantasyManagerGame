package com.mygdx.game.animation;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.BodyType;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.item.OneHandedItem;
import com.mygdx.game.item.weapon.twohandedsword.TwoHandedSword;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.single.*;
import com.mygdx.game.logic.attack.AttackController;
import com.mygdx.game.registry.ActionRegistry;
import com.mygdx.game.registry.AnimationRegistry;
import com.mygdx.game.registry.RendererToolsRegistry;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.renderer.direction.DirectionSelector;

public class FullBodyActorAnimation implements ActorAnimation {

    private TextureRegistry textureRegistry = TextureRegistry.INSTANCE;

    private final DirectionSelector directionSelector = DirectionSelector.INSTANCE;

    private float phase = 0;

    private final float offset = (1f - Config.Engine.ACTOR_HEIGHT) / 2f;

    @Override
    public void drawKeyFrame(SpriteBatch spriteBatch, float x, float y, float scale, Direction direction, Activity activity, Actor actor) {
        phase = (phase + 0.1f) % 3f;

        int row = getRow(activity);

        spriteBatch.setColor(Color.WHITE);
        // weapon
        if (actor.getRightHandItem() != null && !actor.isAttacking()) {
            if(OneHandedItem.class.isAssignableFrom(actor.getRightHandItem().getClass())) {
                spriteBatch.draw(textureRegistry.getFor(actor.getRightHandItem().getClass()), x + offset + (!getFlip(direction) ? 0.0f : -0.3f), y + offset - 0.2f, 0.5f, 0.5f, 1, 1, Config.Engine.ACTOR_HEIGHT, Config.Engine.ACTOR_HEIGHT, getFlip(direction) ? 75f : 115f, 0, 0, textureRegistry.getFor(actor.getRightHandItem().getClass()).getHeight(), textureRegistry.getFor(actor.getRightHandItem().getClass()).getWidth(), true, !getFlip(direction));
            } else if (!TwoHandedSword.class.isAssignableFrom(actor.getRightHandItem().getClass())){
                    spriteBatch.draw(textureRegistry.getFor(actor.getRightHandItem().getClass()), x + offset - 0.15f, y + offset - 0.2f, 0.5f, 0.5f, 1, 1, Config.Engine.ACTOR_HEIGHT, Config.Engine.ACTOR_HEIGHT, getFlip(direction) ? 75f : 115f, 0, 0, textureRegistry.getFor(actor.getRightHandItem().getClass()).getHeight(), textureRegistry.getFor(actor.getRightHandItem().getClass()).getWidth(), true, !getFlip(direction));
            }
        }

        // body
        spriteBatch.setColor(Color.valueOf(actor.getAppearance().getBodyColor()));
        spriteBatch.draw(getBodyTexture(actor), x + offset, y + offset, 0, 0, 1, 1, scale, scale, 0, (int) phase * 32, 32 * row, 32, 32, getFlip(direction), false);

        // eyes
        spriteBatch.setColor(Color.valueOf(actor.getAppearance().getEyesColor()));
        spriteBatch.draw(getEyesTexture(actor), x + offset, y + offset, 0, 0, 1, 1, scale, scale, 0, (int) phase * 32, 32 * row, 32, 32, getFlip(direction), false);

        // hair
        spriteBatch.setColor(Color.valueOf(actor.getAppearance().getHairColor()));
        spriteBatch.draw(getHairTexture(actor), x + offset, y + offset, 0, 0, 1, 1, scale, scale, 0, (int) phase * 32, 32 * row, 32, 32, getFlip(direction), false);

        // armor
        if (actor.getWornArmor() != null) {
            spriteBatch.setColor(Color.WHITE);
            spriteBatch.draw(textureRegistry.getArmor(actor.getBodyType().getArchetype(), actor.getWornArmor().getSimpleName(), actor.getGender()), x + offset, y + offset, 0, 0, 1, 1, scale, scale, 0f, (int) phase * 32, 32 * row, 32, 32, getFlip(direction), false);
        }

        // beard
        if(actor.getAppearance().getBeardIndex() != null) {
            spriteBatch.setColor(Color.valueOf(actor.getAppearance().getHairColor()));
            spriteBatch.draw(getBeardTexture(actor), x + offset, y + offset, 0, 0, 1, 1, scale, scale, 0, (int) phase * 32, 32 * row, 32, 32, getFlip(direction), false);
        }

        spriteBatch.setColor(Color.WHITE);

        if (actor.getRightHandItem() != null && !actor.isAttacking()) {
            if (TwoHandedSword.class.isAssignableFrom(actor.getRightHandItem().getClass())) {
                spriteBatch.draw(textureRegistry.getFor(actor.getRightHandItem().getClass()), x + offset + (!getFlip(direction) ? 0.05f : -0.35f), y + offset - 0.175f, 0.5f, 0.5f, 1, 1, Config.Engine.ACTOR_HEIGHT, Config.Engine.ACTOR_HEIGHT, getFlip(direction) ? 75f : 115f, 0, 0, textureRegistry.getFor(actor.getRightHandItem().getClass()).getHeight(), textureRegistry.getFor(actor.getRightHandItem().getClass()).getWidth(), true, !getFlip(direction));
            }
        }

        // shield
        if (actor.getLeftHandItem() != null) {
            spriteBatch.draw(textureRegistry.getFor(actor.getLeftHandItem().getClass()), actor.getX() + actor.getxOffset() + (1f - Config.Engine.ACTOR_HEIGHT) / 2f, actor.getY() + actor.getyOffset() + (1f - Config.Engine.ACTOR_HEIGHT) / 2f, 0, 0, 1, 1, Config.Engine.ACTOR_HEIGHT, Config.Engine.ACTOR_HEIGHT, 0, 0, 0, textureRegistry.getFor(actor.getLeftHandItem().getClass()).getHeight(), textureRegistry.getFor(actor.getLeftHandItem().getClass()).getWidth(), directionSelector.getDirection(actor).equals(Direction.LEFT) || directionSelector.getDirection(actor).equals(Direction.UP), false);
        }
    }

    private boolean getFlip(Direction direction) {
        return direction.equals(Direction.LEFT) || direction.equals(Direction.UP) ? true : false;
    }

    private Texture getBodyTexture(Actor actor) {
        return textureRegistry.getBody(actor.getBodyType().getArchetype(), "body", actor.getAppearance().getBodyIndex(), actor.getGender());
    }

    private Texture getHairTexture(Actor actor) {
        return textureRegistry.getBody(actor.getBodyType().getArchetype(), "hair", actor.getAppearance().getHairIndex(), actor.getGender());
    }

    private Texture getBeardTexture(Actor actor) {
        return textureRegistry.getBody(actor.getBodyType().getArchetype(), "beard", actor.getAppearance().getBeardIndex(), actor.getGender());
    }

    private Texture getEyesTexture(Actor actor) {
        return textureRegistry.getBody(actor.getBodyType().getArchetype(), "eyes", actor.getAppearance().getEyesIndex(), actor.getGender());
    }

    private int getRow(Activity activity) {
        if (WaitActivity.class.isAssignableFrom(activity.getCurrentClass()) ||
                IdleActivity.class.isAssignableFrom(activity.getCurrentClass()) ||
                TimedIdleActivity.class.isAssignableFrom(activity.getCurrentClass())) {
            return AnimationPhase.WALK.column;
        }

        if (ConsumeHealingPotion.class.isAssignableFrom(activity.getCurrentClass())) {
            return AnimationPhase.WALK.column;
        }

        if (PreCalculatedMovementActivity.class.isAssignableFrom(activity.getCurrentClass()) ||
                MovementActivity.class.isAssignableFrom(activity.getCurrentClass()) ||
                ExplorationActivity.class.isAssignableFrom(activity.getCurrentClass())
        ) {
            return AnimationPhase.WALK.column;
        }

        if (SleepActivity.class.isAssignableFrom(activity.getCurrentClass())) {
            return AnimationPhase.SLEEP_IN_BED.column;
        }

        if (SleepAtCampfireActivity.class.isAssignableFrom(activity.getCurrentClass()) || SleepOutsideActivity.class.isAssignableFrom(activity.getCurrentClass())) {
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
