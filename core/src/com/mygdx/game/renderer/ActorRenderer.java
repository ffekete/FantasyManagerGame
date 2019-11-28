package com.mygdx.game.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.logic.action.manager.ActionManager;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.logic.visibility.VisitedArea;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.AnimationRegistry;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;
import com.mygdx.game.renderer.camera.CameraPositionController;
import com.mygdx.game.renderer.direction.DirectionSelector;
import com.mygdx.game.renderer.gui.component.GuiComponent;

public class ActorRenderer implements Renderer<Map2D> {

    public static final ActorRenderer INSTANCE = new ActorRenderer();

    private final ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    private final TextureRegistry textureRegistry = TextureRegistry.INSTANCE;
    private final DirectionSelector directionSelector = DirectionSelector.INSTANCE;
    private final ActionManager actionManager = ActionManager.INSTANCE;

    TextureRegion textureRegion;
    TextureRegion healthBarRegion;
    TextureRegion manaBarRegion;
    Activity activity;

    public ActorRenderer() {
        this.textureRegion = new TextureRegion(textureRegistry.getFor(GuiComponent.HUD), 0, 150, textureRegistry.getFor(GuiComponent.HUD).getWidth(), textureRegistry.getFor(GuiComponent.HUD).getHeight());
        this.healthBarRegion = new TextureRegion(textureRegistry.getFor(GuiComponent.HUD), 340, 30, 500, 40);
        this.manaBarRegion = new TextureRegion(textureRegistry.getFor(GuiComponent.HUD), 340, 110, 500, 40);
    }

    @Override
    public void draw(Map2D dungeon, SpriteBatch spriteBatch) {

        if (CameraPositionController.INSTANCE.getZoom() > Config.Engine.ZOOM_MAX_TO_SMALL_MAP) {
            return;
        }

        VisibilityMask visibilityMask = VisibilityMapRegistry.INSTANCE.getFor(dungeon);

        spriteBatch.setColor(Color.WHITE);
        for (Actor actor : actorRegistry.getActors(dungeon)) {
            if (Alignment.FRIENDLY.equals(actor.getAlignment()) || visibilityMask == null || !visibilityMask.getValue(actor.getX(), actor.getY()).isEmpty())
                if (AnimationRegistry.INSTANCE.getAnimations().containsKey(actor) && dungeon.getVisitedareaMap()[actor.getX()][actor.getY()] != VisitedArea.NOT_VISITED) {
                    activity = actor.getCurrentActivity();

                    spriteBatch.setColor(Color.valueOf("FFFFFF88"));
                    spriteBatch.draw(textureRegistry.getShadowTexture(), actor.getX() + actor.getxOffset() + 0.25f, actor.getY() + actor.getyOffset() + 0.25f, 0.5f, 0.5f);

                    spriteBatch.setColor(Color.valueOf("FFFFFFFF"));
                    AnimationRegistry.INSTANCE.getAnimations().get(actor).drawKeyFrame(spriteBatch, actor.getX() + actor.getxOffset(), actor.getY() + actor.getyOffset(), Config.Engine.ACTOR_HEIGHT, directionSelector.getDirection(actor), activity, actor);
                }

            if (dungeon.getVisitedareaMap()[actor.getX()][actor.getY()] != VisitedArea.NOT_VISITED) {
                // show health bar
                spriteBatch.draw(healthBarRegion, actor.getX() + actor.getxOffset() + 0.2f, actor.getY() + actor.getyOffset() + 1, 1.8f * ((float) actor.getHp() / actor.getMaxHp()), 0.1f);

                // show mana bar
                spriteBatch.draw(manaBarRegion, actor.getX() + actor.getxOffset() + 0.2f, actor.getY() + actor.getyOffset() + 1.1f, 1.8f * ((float) actor.getMana() / actor.getMaxMana()), 0.1f);

                // show shield
                if (actor.getLeftHandItem() != null) {
                    spriteBatch.draw(textureRegistry.getFor(actor.getLeftHandItem().getClass()), actor.getX() + actor.getxOffset() + (1f - Config.Engine.ACTOR_HEIGHT) / 2f, actor.getY() + actor.getyOffset() + (1f - Config.Engine.ACTOR_HEIGHT) / 2f, 0, 0, 1, 1, Config.Engine.ACTOR_HEIGHT, Config.Engine.ACTOR_HEIGHT, 0, 0, 0, textureRegistry.getFor(actor.getLeftHandItem().getClass()).getHeight(), textureRegistry.getFor(actor.getLeftHandItem().getClass()).getWidth(), directionSelector.getDirection(actor).equals(Direction.LEFT) || directionSelector.getDirection(actor).equals(Direction.UP), false);
                }
            }
        }
    }


}
