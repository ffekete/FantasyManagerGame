package com.mygdx.game.registry;

import com.badlogic.gdx.graphics.Texture;
import com.google.common.collect.ImmutableMap;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.potion.SmallHealingPotion;
import com.mygdx.game.item.weapon.bow.LongBow;
import com.mygdx.game.item.weapon.staff.JadeStaff;
import com.mygdx.game.item.weapon.sword.*;
import com.mygdx.game.logic.action.Action;
import com.mygdx.game.logic.action.SlowAction;
import com.mygdx.game.map.TileBase;
import com.mygdx.game.map.dungeon.Tile;
import com.mygdx.game.item.shield.MediumShield;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.decoration.TreasureChest;
import com.mygdx.game.object.decoration.Tree;
import com.mygdx.game.object.interactive.DungeonEntrance;
import com.mygdx.game.map.worldmap.WorldMapTile;
import com.mygdx.game.item.food.Bread;
import com.mygdx.game.item.shield.SmallShiled;
import com.mygdx.game.object.interactive.Ladder;
import com.mygdx.game.object.light.LightSourceType;
import com.mygdx.game.renderer.gui.component.GuiComponent;
import com.mygdx.game.resolver.ModdablePathResolver;
import com.mygdx.game.resolver.PathResolver;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TextureRegistry {

    public static final TextureRegistry INSTANCE = new TextureRegistry();

    private final PathResolver<Texture> texturePathResolver;

    private Map<Class<? extends Action>, Texture> actionTextures;
    private Map<GuiComponent, Texture> guiTextures;
    private Map<Class, Texture> itemTextures;
    private Map<LightSourceType, Texture> lightTextures;
    private Map<TileBase, Texture> mapTextures;
    private Map<Class<? extends WorldObject>, List<Texture>> objectTextures;

    public TextureRegistry() {

        texturePathResolver = new ModdablePathResolver();

        actionTextures = ImmutableMap.<Class<? extends Action>, Texture>builder()
                .put(SlowAction.class, texturePathResolver.resolve("effects/Slow.png"))
                .build();


        guiTextures = ImmutableMap.<GuiComponent, Texture>builder()
                .put(GuiComponent.HUD, texturePathResolver.resolve("Hud.png"))
                .build();

        lightTextures = ImmutableMap.<LightSourceType, Texture>builder()
                .put(LightSourceType.Ambient, texturePathResolver.resolve("light_bu.png"))
                .put(LightSourceType.Beam, texturePathResolver.resolve("mist.jpg"))
                .build();

        itemTextures = ImmutableMap.<Class, Texture>builder()
                .put(ShortSword.class, texturePathResolver.resolve("items/ShortSword.png"))
                .put(ShortSwordPlusOne.class, texturePathResolver.resolve("items/ShortSword.png"))
                .put(ShortSwordPlusFour.class, texturePathResolver.resolve("items/ShortSword.png"))
                .put(Bread.class, texturePathResolver.resolve("items/bread.png"))
                .put(SmallHealingPotion.class, texturePathResolver.resolve("items/HealingPotion.png"))
                .put(SmallShiled.class, texturePathResolver.resolve("items/SmallShield.png"))
                .put(MediumShield.class, texturePathResolver.resolve("items/MediumShield.png"))
                .put(PoisonFang.class, texturePathResolver.resolve("items/PoisonFang.png"))
                .put(FlameTongue.class, texturePathResolver.resolve("items/Flametongue.png"))
                .put(JadeStaff.class, texturePathResolver.resolve("items/JadeStaff.png"))
                .put(LongBow.class, texturePathResolver.resolve("items/LongBow.png"))
                .build();

        mapTextures = ImmutableMap.<TileBase, Texture>builder()
                .put(WorldMapTile.GRASS, texturePathResolver.resolve("terrain.jpg"))
                .put(Tile.STONE_WALL, texturePathResolver.resolve("wall.png"))
                .put(Tile.FLOOR, texturePathResolver.resolve("terrain.jpg"))
                .put(Tile.EMPTY, texturePathResolver.resolve("void.png"))
                .build();


        mapTextures.forEach((tile, texture) -> {
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        });

        objectTextures = ImmutableMap.<Class<? extends WorldObject>, List<Texture>>builder()
                .put(DungeonEntrance.class, Arrays.asList(texturePathResolver.resolve("object/DungeonEntrance.png")))
                .put(Ladder.class, Arrays.asList(texturePathResolver.resolve("object/Ladder.png")))
                .put(Tree.class, Arrays.asList(texturePathResolver.resolve("object/Tree.png")))
                .put(TreasureChest.class, Arrays.asList(texturePathResolver.resolve("object/TreasureChest.png"),
                        texturePathResolver.resolve("object/OpenTreasureChest.png")))
                .build();
    }

    public Texture getActionTexture(Class<? extends Action> clazz) {
        return actionTextures.get(clazz);
    }

    public Texture getFor(GuiComponent guiComponent) {
        return guiTextures.get(guiComponent);
    }

    public Texture getFor(LightSourceType lightSourceType) {
        return lightTextures.get(lightSourceType);
    }

    public List<Texture> getForobject(Class<? extends WorldObject> objectClass) {
        return objectTextures.get(objectClass);
    }


    public Texture getForTile(TileBase tileBase) {
        return mapTextures.get(tileBase);
    }

    public Texture getFor(Class<? extends Item> clazz) {
        if (itemTextures.containsKey(clazz)) {
            return itemTextures.get(clazz);
        }
        return null;
    }

    public void dispose() {
        for (Texture t : itemTextures.values()) {
            t.dispose();
        }
    }
}
