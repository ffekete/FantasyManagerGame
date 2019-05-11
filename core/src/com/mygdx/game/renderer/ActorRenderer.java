package com.mygdx.game.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.logic.action.manager.ActionManager;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.single.ExplorationActivity;
import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.AnimationRegistry;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;
import com.mygdx.game.renderer.direction.DirectionDecision;

public class ActorRenderer implements Renderer {

    public static final ActorRenderer INSTANCE = new ActorRenderer();

    private final ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    private final TextureRegistry textureRegistry = TextureRegistry.INSTANCE;
    private final DirectionDecision directionDecision = DirectionDecision.INSTANCE;
    private final ActionManager actionManager = ActionManager.INSTANCE;

    private Texture targetTexture = new Texture(Gdx.files.internal("location.png"));

    @Override
    public void draw(Map2D dungeon, SpriteBatch spriteBatch) {
        VisibilityMask visibilityMask = VisibilityMapRegistry.INSTANCE.getFor(dungeon);

        spriteBatch.setColor(Color.WHITE);
        for (Actor actor : actorRegistry.getActors(dungeon)) {
            if (Alignment.FRIENDLY.equals(actor.getAlignment()) || visibilityMask == null || !visibilityMask.getValue(actor.getX(), actor.getY()).isEmpty())
                if (AnimationRegistry.INSTANCE.getAnimations().containsKey(actor)) {
                    Activity activity = actor.getCurrentActivity();
                    AnimationRegistry.INSTANCE.getAnimations().get(actor).drawKeyFrame(spriteBatch, actor.getX() + actor.getxOffset(), actor.getY() + actor.getyOffset(), 1, directionDecision.getDirection(actor), activity, actor.getClass());
                }

            if(actor.getLeftHandItem() != null) {
                Texture itemTexture = textureRegistry.getFor(actor.getLeftHandItem().getClass());
                spriteBatch.draw(itemTexture, actor.getX() + actor.getxOffset(), actor.getY() + actor.getyOffset(), 0, 0, 1, 1, 1, 1, 0, 0, 0, itemTexture.getHeight(), itemTexture.getWidth(), directionDecision.getDirection(actor).equals(Direction.LEFT) || directionDecision.getDirection(actor).equals(Direction.UP), false);
            }

            if (ExplorationActivity.class.isAssignableFrom(actor.getActivityStack().getCurrent().getClass())) {
                ExplorationActivity explorationActivity = (ExplorationActivity) actor.getActivityStack().getCurrent();
                spriteBatch.draw(targetTexture, explorationActivity.getTargetX(), explorationActivity.getTargetY(), 0, 0, 1, 1, 1, 1, 0, 0, 0, targetTexture.getHeight(), targetTexture.getWidth(), false, false);
            }

            actionManager.update();
        }
    }



}
