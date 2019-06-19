package com.mygdx.game.registry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.google.common.collect.ImmutableMap;
import com.mygdx.game.item.potion.SmallHealingPotion;
import com.mygdx.game.item.weapon.staff.JadeStaff;
import com.mygdx.game.map.TileBase;
import com.mygdx.game.map.dungeon.Tile;
import com.mygdx.game.item.shield.MediumShield;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.decoration.Tree;
import com.mygdx.game.object.interactive.DungeonEntrance;
import com.mygdx.game.map.worldmap.WorldMapTile;
import com.mygdx.game.item.food.Bread;
import com.mygdx.game.item.shield.SmallShiled;
import com.mygdx.game.item.weapon.sword.FlameTongue;
import com.mygdx.game.item.weapon.sword.PoisonFang;
import com.mygdx.game.item.weapon.sword.ShortSword;
import com.mygdx.game.object.light.LightSourceType;
import com.mygdx.game.renderer.gui.component.GuiComponent;

import java.util.Map;

public class TextureRegistry {

    public static final TextureRegistry INSTANCE = new TextureRegistry();

    private Map<GuiComponent, Texture> guiTextures;
    private Map<Class, Texture> textures;
    private Map<LightSourceType, Texture> lightTextures;
    private Map<TileBase, Texture> mapTextures;
    private Map<Class<? extends WorldObject>, Texture> objectTextures;

    public TextureRegistry() {



        guiTextures = ImmutableMap.<GuiComponent, Texture>builder()
                .put(GuiComponent.HUD, new Texture(Gdx.files.internal("Hud.png")))
                .build();

        lightTextures = ImmutableMap.<LightSourceType, Texture>builder()
                .put(LightSourceType.Ambient, new Texture(Gdx.files.internal("light_bu.png")))
                .put(LightSourceType.Beam, new Texture(Gdx.files.internal("mist.jpg")))
                .build();

        textures = ImmutableMap.<Class, Texture>builder()
                .put(ShortSword.class, new Texture(Gdx.files.internal("ShortSword.png")))
                .put(Bread.class, new Texture(Gdx.files.internal("bread.png")))
                .put(SmallHealingPotion.class, new Texture(Gdx.files.internal("HealingPotion.png")))
                .put(SmallShiled.class, new Texture(Gdx.files.internal("SmallShield.png")))
                .put(MediumShield.class, new Texture(Gdx.files.internal("MediumShield.png")))
                .put(PoisonFang.class, new Texture(Gdx.files.internal("PoisonFang.png")))
                .put(FlameTongue.class, new Texture(Gdx.files.internal("Flametongue.png")))
                .put(JadeStaff.class, new Texture(Gdx.files.internal("JadeStaff.png")))
                .build();

        mapTextures = ImmutableMap.<TileBase, Texture>builder()
                .put(WorldMapTile.GRASS, new Texture(Gdx.files.internal("terrain.jpg")))
                .put(Tile.STONE_WALL, new Texture(Gdx.files.internal("wall.png")))
                .put(Tile.FLOOR, new Texture(Gdx.files.internal("terrain.jpg")))
                .put(Tile.EMPTY, new Texture(Gdx.files.internal("void.png")))
                .build();



        mapTextures.forEach((tile, texture) -> {
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        });

        objectTextures = ImmutableMap.<Class<? extends WorldObject>, Texture>builder()
                .put(DungeonEntrance.class, new Texture(Gdx.files.internal("DungeonEntrance.png")))
                .put(Tree.class, new Texture(Gdx.files.internal("object/Tree.png")))
                .build();
    }

    public Texture getFor(GuiComponent guiComponent) {
        return guiTextures.get(guiComponent);
    }

    public Texture getFor(LightSourceType lightSourceType) {
        return lightTextures.get(lightSourceType);
    }

    public Texture getForobject(Class<? extends WorldObject> objectClass) {
        return objectTextures.get(objectClass);
    }


    public Texture getForTile(TileBase tileBase) {
        return mapTextures.get(tileBase);
    }

    public Texture getFor(Class clazz) {
        if(textures.containsKey(clazz)) {
            return textures.get(clazz);
        }
        return null;
    }

    public void dispose() {
        for(Texture t : textures.values()) {
            t.dispose();
        }
    }
}
