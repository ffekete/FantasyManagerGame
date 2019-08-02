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
import com.mygdx.game.menu.MenuItem;
import com.mygdx.game.registry.RendererToolsRegistry;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.renderer.Renderer;
import com.mygdx.game.rules.levelup.ExperienceLevelMapper;
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

        RendererToolsRegistry.INSTANCE.getSpriteBatch().draw(textureRegistry.getFor(MenuItem.Inventory), 0, 0, 0, 0, Config.Screen.WIDTH, Config.Screen.HEIGHT);

        int i = 2;
        // Name
        RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), actor.getName() + " (" + actor.getActorClass() + ")", screenX / 2f, screenY - i * 35);

        i = 6;
        // level
        RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "Level:", 200, screenY - i * 35);
        RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "" + actor.getLevel(), 470, screenY - i * 35);
        i++;
        // Exp
        RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "Exp:", 200, screenY - i * 35);
        RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "" + actor.getExperiencePoints() + " / " + ExperienceLevelMapper.INSTANCE.getForLevel(actor.getLevel() + 1), 470, screenY - i * 35);

        // attributes
        i = 8;
        for(Attributes attribute : Attributes.values()) {
            RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), attribute.name(), 200, screenY - i * 35);
            RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "" + actor.getAttribute(attribute), 470, screenY - i * 35);
            i++;
        }

        i++;
        // HP
        RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "HP", 200, screenY - i * 35);
        RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "" + actor.getMaxHp() + " / " + actor.getHp(), 470, screenY - i * 35);
        i++;

        // MP
        RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "MP", 200, screenY - i * 35);
        RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "" + actor.getMaxMana() + " / " + actor.getMana(), 470, screenY - i * 35);

        // WeaponSkills
        i = 5;
        for(WeaponSkill skill : WeaponSkill.values()) {
            RendererToolsRegistry.INSTANCE.getBitmapFontSmall().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), skill.name(), 800, screenY - i * 35);
            RendererToolsRegistry.INSTANCE.getBitmapFontSmall().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), getPluses(actor.getWeaponSkills().get(skill)), 1100, screenY - i * 35);
            i++;
        }

        // MagicSkills
        for(MagicSkill skill : MagicSkill.values()) {
            RendererToolsRegistry.INSTANCE.getBitmapFontSmall().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), skill.name(), 800, screenY - i * 35);
            RendererToolsRegistry.INSTANCE.getBitmapFontSmall().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), getPluses(actor.getMagicSkills().get(skill)), 1100, screenY - i * 35);
            i++;
        }

        // items
        i = 0;
        int row = 0;
        for(int j = 0; j < actor.getInventory().size(); j++) {
            int x = i * 39 + 190;

            int arow = row;

            actor.getInventory().get(j).ifPresent(item -> {
                RendererToolsRegistry.INSTANCE.getSpriteBatch().draw(textureRegistry.getFor(item.getClass()), x, 190 - arow * 40, 16, 16, 28, 28, 2f, 2f, 0, 0, 0, 32, 32, false, false);
            });

            i++;
            if( i == 26) {
                i = 0;
                row++;
            }
        }

        if(actor.getRightHandItem() != null)
            RendererToolsRegistry.INSTANCE.getSpriteBatch().draw(textureRegistry.getFor(actor.getRightHandItem().getClass()), 1530, 1020 - 330, 16, 16, 28, 28, 3f, 3f, 45f, 0, 0, 32, 32, false, false);

        RendererToolsRegistry.INSTANCE.getSpriteBatch().end();

        rendererToolsRegistry.getStage(GameState.Inventory).act();
        rendererToolsRegistry.getStage(GameState.Inventory).draw();
    }

    private String getPluses(int amount) {
        StringBuilder stringBuilder = new StringBuilder("+");
        for(int i = 0; i < amount; i++) {
            stringBuilder.append("+");
        }
        return stringBuilder.toString();
    }
}
