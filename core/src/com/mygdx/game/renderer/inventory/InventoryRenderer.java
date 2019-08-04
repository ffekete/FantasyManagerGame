package com.mygdx.game.renderer.inventory;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.component.attribute.Attributes;
import com.mygdx.game.actor.component.skill.MagicSkill;
import com.mygdx.game.actor.component.skill.WeaponSkill;
import com.mygdx.game.logic.GameState;
import com.mygdx.game.logic.Point;
import com.mygdx.game.menu.MenuItem;
import com.mygdx.game.registry.RendererToolsRegistry;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.renderer.Renderer;
import com.mygdx.game.rules.levelup.ExperienceLevelMapper;
import com.mygdx.game.utils.GdxUtils;

import java.util.Optional;

public class InventoryRenderer implements Renderer<Actor> {

    public static final InventoryRenderer INSTANCE = new InventoryRenderer();
    public static final int ATTRIBUTES_PANEL_X = 200;
    public static final int SKILLS_PANEL_X = 800;
    public static final int SKILL_VALUE_COLUMN_X = 1100;
    public static final int ATTRIBUTE_VALUE_COLUMN_X = 430;
    public static final int RIGHT_HAND_X = 1800;
    public static final int RIGHT_HAND_Y = 1020 - 330;
    public static final int LEFT_HAND_X = 1530;
    public static final int LEFT_HAND_Y = 1020 - 320;

    public static final int INVENTORY_LEFT_X = 175;
    public static final int INVENTORY_LEFT_Y = 265;
    public static final int INVENTORY_RIGHT_X = 1210;
    public static final int INVENTORY_RIGHT_Y = 120;
    public static final int INVENTORY_ROW_LENGTH = 16;

    private String itemText = null;

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
        RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "Level:", ATTRIBUTES_PANEL_X, screenY - i * 35);
        RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "" + actor.getLevel(), ATTRIBUTE_VALUE_COLUMN_X, screenY - i * 35);
        i++;
        // Exp
        RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "Exp:", ATTRIBUTES_PANEL_X, screenY - i * 35);
        RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "" + actor.getExperiencePoints() + " / " + ExperienceLevelMapper.INSTANCE.getForLevel(actor.getLevel() + 1), 430, screenY - i * 35);

        // attributes
        i = 8;
        for(Attributes attribute : Attributes.values()) {
            RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), attribute.name(), ATTRIBUTES_PANEL_X, screenY - i * 35);
            RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "" + actor.getAttribute(attribute), ATTRIBUTE_VALUE_COLUMN_X, screenY - i * 35);
            i++;
        }

        i++;
        // HP
        RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "HP", ATTRIBUTES_PANEL_X, screenY - i * 35);
        RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "" + actor.getMaxHp() + " / " + actor.getHp(), ATTRIBUTE_VALUE_COLUMN_X, screenY - i * 35);
        i++;

        // MP
        RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "MP", ATTRIBUTES_PANEL_X, screenY - i * 35);
        RendererToolsRegistry.INSTANCE.getBitmapFont().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), "" + actor.getMaxMana() + " / " + actor.getMana(), ATTRIBUTE_VALUE_COLUMN_X, screenY - i * 35);

        // WeaponSkills
        i = 5;
        for(WeaponSkill skill : WeaponSkill.values()) {
            RendererToolsRegistry.INSTANCE.getBitmapFontSmall().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), skill.name(), SKILLS_PANEL_X, screenY - i * 35);
            RendererToolsRegistry.INSTANCE.getBitmapFontSmall().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), getPluses(actor.getWeaponSkills().getOrDefault(skill, 0)), SKILL_VALUE_COLUMN_X, screenY - i * 35);
            i++;
        }

        // MagicSkills
        for(MagicSkill skill : MagicSkill.values()) {
            RendererToolsRegistry.INSTANCE.getBitmapFontSmall().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), skill.name(), SKILLS_PANEL_X, screenY - i * 35);
            RendererToolsRegistry.INSTANCE.getBitmapFontSmall().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), getPluses(actor.getMagicSkills().getOrDefault(skill, 0)), SKILL_VALUE_COLUMN_X, screenY - i * 35);
            i++;
        }

        // items
        i = 0;
        int row = 0;
        for(int j = 0; j < actor.getInventory().size(); j++) {
            int x = i * 64 + INVENTORY_LEFT_X;

            int arow = row;

            actor.getInventory().get(j).ifPresent(item -> {
                RendererToolsRegistry.INSTANCE.getSpriteBatch().draw(textureRegistry.getFor(item.getClass()), x, INVENTORY_LEFT_X - arow * 64 + 5, 0, 0, 64, 64, 1f, 1f, 0, 0, 0, 32, 32, false, false);
            });

            i++;
            if( i == INVENTORY_ROW_LENGTH) {
                i = 0;
                row++;
            }
        }

        // left hand
        if(actor.getLeftHandItem() != null)
            RendererToolsRegistry.INSTANCE.getSpriteBatch().draw(textureRegistry.getFor(actor.getLeftHandItem().getClass()), LEFT_HAND_X, LEFT_HAND_Y, 16, 16, 28, 28, 3f, 3f, 45f, 0, 0, 32, 32, false, false);

        // right hand
        if(actor.getRightHandItem() != null)
            RendererToolsRegistry.INSTANCE.getSpriteBatch().draw(textureRegistry.getFor(actor.getRightHandItem().getClass()), RIGHT_HAND_X, RIGHT_HAND_Y, 16, 16, 28, 28, 3f, 3f, 45f, 0, 0, 32, 32, false, false);

        if(itemText != null) {
            RendererToolsRegistry.INSTANCE.getBitmapFontSmallest().draw(RendererToolsRegistry.INSTANCE.getSpriteBatch(), itemText, 1420, 280);
        }



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

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }
}
