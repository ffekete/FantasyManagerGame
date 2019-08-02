package com.mygdx.game.renderer.inventory;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.component.attribute.Attributes;
import com.mygdx.game.actor.component.skill.MagicSkill;
import com.mygdx.game.actor.component.skill.WeaponSkill;
import com.mygdx.game.logic.GameState;
import com.mygdx.game.registry.RendererToolsRegistry;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.renderer.Renderer;
import com.mygdx.game.utils.GdxUtils;

public class InventoryRenderer implements Renderer<Actor> {

    public static final InventoryRenderer INSTANCE = new InventoryRenderer();

    private final RendererToolsRegistry rendererToolsRegistry = RendererToolsRegistry.INSTANCE;
    private final TextureRegistry textureRegistry = TextureRegistry.INSTANCE;

    @Override
    public void draw(Actor actor, SpriteBatch spriteBatch) {
        GdxUtils.clearScreen();

        int screenX = com.mygdx.game.Config.Screen.CANVAS_WIDTH;
        int screenY = Config.Screen.HEIGHT;

        RendererToolsRegistry.INSTANCE.getInfoViewPort().apply();
        RendererToolsRegistry.INSTANCE.getSpriteBatch().begin();

        int i = 1;
        for(Attributes attribute : Attributes.values()) {
            RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), attribute.name(), 10, screenY - i * 35);
            RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "" + actor.getAttribute(attribute), 250, screenY - i * 35);
            i++;
        }

        RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "HP", 10, screenY - i * 35);
        RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "" + actor.getMaxHp() + " / " + actor.getHp(), 180, screenY - i * 35);
        i++;
        RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "MP", 10, screenY - i * 35);
        RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "" + actor.getMaxMana() + " / " + actor.getMana(), 180, screenY - i * 35);

        i = 1;
        for(WeaponSkill skill : WeaponSkill.values()) {
            RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), skill.name(), 350, screenY - i * 35);
            RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "" + actor.getWeaponSkills().get(skill), 700, screenY - i * 35);
            i++;
        }

        for(MagicSkill skill : MagicSkill.values()) {
            RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), skill.name(), 350, screenY - i * 35);
            RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "" + actor.getMagicSkills().get(skill), 700, screenY - i * 35);
            i++;
        }

        for(int j = 0; j < actor.getInventory().size(); j++) {
            int x = j * 35 + 16;
            actor.getInventory().get(j).ifPresent(item -> {
                RendererToolsRegistry.INSTANCE.getSpriteBatch().draw(textureRegistry.getFor(item.getClass()), x, 50, 16, 16, 28, 28, 2f, 2f, 0, 0, 0, 32, 32, false, false);
            });
        }

        RendererToolsRegistry.INSTANCE.getSpriteBatch().end();

        rendererToolsRegistry.getStage(GameState.Inventory).act();
        rendererToolsRegistry.getStage(GameState.Inventory).draw();
    }
}
