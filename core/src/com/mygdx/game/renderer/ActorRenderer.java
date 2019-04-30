package com.mygdx.game.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.ExplorationActivity;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.AnimationRegistry;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;

public class ActorRenderer implements Renderer {

    public static final ActorRenderer INSTANCE = new ActorRenderer();

    private final ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    private final TextureRegistry textureRegistry = TextureRegistry.INSTANCE;

    private Texture actorTexture = new Texture(Gdx.files.internal("warrior.png"));
    private Texture targetTexture = new Texture(Gdx.files.internal("location.png"));

    @Override
    public void draw(Map2D dungeon, SpriteBatch spriteBatch) {
        VisibilityMask visibilityMask = VisibilityMapRegistry.INSTANCE.getFor(dungeon);

        spriteBatch.setColor(Color.WHITE);
        for (Actor actor : actorRegistry.getActors(dungeon)) {
            if (Alignment.FRIENDLY.equals(actor.getAlignment()) || !visibilityMask.getValue(actor.getX(), actor.getY()).isEmpty())
                if (AnimationRegistry.INSTANCE.getAnimations().containsKey(actor)) {
                    Activity activity = actor.getCurrentActivity();

                    // if no actorAnimation is registered in animationRegistry for that activity type, draw a placeholder
                    if (!AnimationRegistry.INSTANCE.getAnimations().containsKey(actor)) {
                        spriteBatch.draw(textureRegistry.getFor(actor.getClass()), actor.getX() - 1 + actor.getxOffset(), actor.getY() - 1 + actor.getyOffset(), 0, 0, 3, 3, 1, 1, 0, 0, 0, actorTexture.getWidth(), actorTexture.getHeight(), false, false);
                        continue;
                    }

                    AnimationRegistry.INSTANCE.getAnimations().get(actor).drawKeyFrame(spriteBatch, actor.getX() - 1 + actor.getxOffset(), actor.getY() - 1 + actor.getyOffset(), 5, ActorMovementHandler.INSTANCE.getDirection(actor), activity);
                } else {
                    spriteBatch.draw(textureRegistry.getFor(actor.getClass()), actor.getX() - 1 + actor.getxOffset(), actor.getY() - 1 + actor.getyOffset(), 0, 0, 3, 3, 1, 1, 0, 0, 0, actorTexture.getWidth(), actorTexture.getHeight(), false, false);

                }

            if (ExplorationActivity.class.isAssignableFrom(actor.getActivityStack().getCurrent().getClass())) {
                ExplorationActivity explorationActivity = (ExplorationActivity) actor.getActivityStack().getCurrent();
                spriteBatch.draw(targetTexture, explorationActivity.getTargetX(), explorationActivity.getTargetY(), -2, -2, 2, 2, 1, 1, 0, 0, 0, targetTexture.getHeight(), targetTexture.getWidth(), false, false);
            }
        }
    }

}
