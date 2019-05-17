package com.mygdx.game.renderer.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.renderer.gui.component.GuiComponent;

public class ActorGuiRenderer implements GuiRenderer {

    public static final ActorGuiRenderer INSTANCE = new ActorGuiRenderer();

    private final TextureRegistry textureRegistry = TextureRegistry.INSTANCE;

    TextureRegion textureRegion;
    TextureRegion healthBarRegion;

    public ActorGuiRenderer() {
        this.textureRegion = new TextureRegion(textureRegistry.getFor(GuiComponent.HUD), 0, 150, textureRegistry.getFor(GuiComponent.HUD).getWidth(), textureRegistry.getFor(GuiComponent.HUD).getHeight());
        this.healthBarRegion = new TextureRegion(textureRegistry.getFor(GuiComponent.HUD), 340, 30, 500, 40);
    }

    @Override
    public void render(SpriteBatch spriteBatch, Actor actors) {
        if(actors != null)
            spriteBatch.draw(healthBarRegion, 100, 47, 300 * ((float)actors.getHp() / (float)actors.getMaxHp()), 30);
        spriteBatch.draw(textureRegion, 10, -100, 300, 200);
    }
}
