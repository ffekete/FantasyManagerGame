package com.mygdx.game.registry;

import com.badlogic.gdx.graphics.Texture;
import com.google.common.collect.ImmutableMap;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Ranger;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.hero.Wizard;
import com.mygdx.game.actor.monster.*;
import com.mygdx.game.actor.worker.Builder;
import com.mygdx.game.actor.worker.Smith;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.buildertool.Hammer;
import com.mygdx.game.item.food.Bread;
import com.mygdx.game.item.potion.SmallAntiVenomPotion;
import com.mygdx.game.item.potion.SmallHealingPotion;
import com.mygdx.game.item.potion.SmallManaPotion;
import com.mygdx.game.item.shield.MediumShield;
import com.mygdx.game.item.shield.SmallShiled;
import com.mygdx.game.item.weapon.bow.LongBow;
import com.mygdx.game.item.weapon.staff.JadeStaff;
import com.mygdx.game.item.weapon.sword.*;
import com.mygdx.game.logic.action.*;
import com.mygdx.game.map.TileBase;
import com.mygdx.game.map.dungeon.DungeonType;
import com.mygdx.game.map.dungeon.Tile;
import com.mygdx.game.map.worldmap.WorldMapTile;
import com.mygdx.game.menu.MenuItem;
import com.mygdx.game.object.AnimatedObject;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.decoration.*;
import com.mygdx.game.object.floor.*;
import com.mygdx.game.object.furniture.IncompleteAnvil;
import com.mygdx.game.object.furniture.IncompletePracticeFigure;
import com.mygdx.game.object.furniture.IncompleteWoodenBed;
import com.mygdx.game.object.furniture.WoodenBed;
import com.mygdx.game.object.interactive.*;
import com.mygdx.game.object.light.LightSourceType;
import com.mygdx.game.item.resources.Wood;
import com.mygdx.game.object.wall.*;
import com.mygdx.game.renderer.gui.component.GuiComponent;
import com.mygdx.game.resolver.ModdablePathResolver;
import com.mygdx.game.resolver.PathResolver;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TextureRegistry {

    public static final TextureRegistry INSTANCE = new TextureRegistry(new ModdablePathResolver());

    private Map<Class<? extends Action>, Optional<Texture>> actionTextures;
    private Map<GuiComponent, Optional<Texture>> guiTextures;
    private Map<Class, Optional<Texture>> itemTextures;
    private Map<LightSourceType, Optional<Texture>> lightTextures;
    private Map<TileBase, Optional<Texture>> mapTextures;
    private Map<Class<? extends WorldObject>, List<Optional<Texture>>> objectTextures;
    private Map<Class<? extends Actor>, Optional<Texture>> characterAnimationTextures;
    private Map<Class<? extends AnimatedObject>, Optional<Texture>> objectAnimationTextures;
    private Map<DungeonType, Optional<Texture>> dungeonTilesetTextures;
    private Map<MenuItem, Optional<Texture>> menuItems;
    private Optional<Texture> shadowTexture;

    public TextureRegistry(PathResolver<Texture> texturePathResolver) {

        shadowTexture = texturePathResolver.resolve("object/Shadow.png");

        menuItems = ImmutableMap.<MenuItem, Optional<Texture>>builder()
                .put(MenuItem.Inventory, texturePathResolver.resolve("menu/Inventory.png"))
                .put(MenuItem.Window, texturePathResolver.resolve("menu/Window.png"))
                .build();

        characterAnimationTextures = ImmutableMap.<Class<? extends Actor>, Optional<Texture>>builder()
                // Heroes
                .put(Ranger.class, texturePathResolver.resolve("actors/Ranger.png"))
                .put(Warrior.class, texturePathResolver.resolve("actors/Warrior.png"))
                .put(Wizard.class, texturePathResolver.resolve("actors/Wizard.png"))

                // Monsters
                .put(Skeleton.class, texturePathResolver.resolve("actors/Skeleton.png"))
                .put(Goblin.class, texturePathResolver.resolve("actors/goblin.png"))
                .put(Orc.class, texturePathResolver.resolve("actors/orc.png"))
                .put(SkeletonWarrior.class, texturePathResolver.resolve("actors/SkeletonWarrior.png"))
                .put(Lich.class, texturePathResolver.resolve("actors/Lich.png"))

                // workers
                .put(Builder.class, texturePathResolver.resolve("actors/worker/Builder.png"))
                .put(Smith.class, texturePathResolver.resolve("actors/worker/Smith.png"))
                .build();

        actionTextures = ImmutableMap.<Class<? extends Action>, Optional<Texture>>builder()
                .put(SlowAction.class, texturePathResolver.resolve("effects/Slow.png"))
                .put(FireboltAction.class, texturePathResolver.resolve("effects/FireBolt.png"))
                .put(BowAction.class, texturePathResolver.resolve("effects/BowEffect.png"))
                .put(ArrowAction.class, texturePathResolver.resolve("effects/Arrow.png"))
                .put(PoisonCloudAction.class, texturePathResolver.resolve("effects/PoisonCloud.png"))
                .put(ExplosionAction.class, texturePathResolver.resolve("effects/ExplosionEffect.png"))
                .put(SparksAction.class, texturePathResolver.resolve("effects/Sparks.png"))
                .put(SmallExplosionAction.class, texturePathResolver.resolve("effects/ExplosionEffect.png"))
                .put(TargetMarkerAction.class, texturePathResolver.resolve("ui/marker/TargetMarker.png"))
                .build();

        guiTextures = ImmutableMap.<GuiComponent, Optional<Texture>>builder()
                .put(GuiComponent.HUD, texturePathResolver.resolve("Hud.png"))
                .build();

        lightTextures = ImmutableMap.<LightSourceType, Optional<Texture>>builder()
                .put(LightSourceType.Ambient, texturePathResolver.resolve("light_bu.png"))
                .put(LightSourceType.Beam, texturePathResolver.resolve("mist.jpg"))
                .build();

        itemTextures = ImmutableMap.<Class, Optional<Texture>>builder()
                .put(ShortSword.class, texturePathResolver.resolve("items/ShortSword.png"))
                .put(ShortSwordPlusOne.class, texturePathResolver.resolve("items/ShortSword.png"))
                .put(ShortSwordPlusFour.class, texturePathResolver.resolve("items/ShortSword.png"))
                .put(Bread.class, texturePathResolver.resolve("items/bread.png"))
                .put(SmallHealingPotion.class, texturePathResolver.resolve("items/HealingPotion.png"))
                .put(SmallManaPotion.class, texturePathResolver.resolve("items/ManaPotion.png"))
                .put(SmallAntiVenomPotion.class, texturePathResolver.resolve("items/AntiVenom.png"))
                .put(SmallShiled.class, texturePathResolver.resolve("items/SmallShield.png"))
                .put(MediumShield.class, texturePathResolver.resolve("items/MediumShield.png"))
                .put(PoisonFang.class, texturePathResolver.resolve("items/PoisonFang.png"))
                .put(FlameTongue.class, texturePathResolver.resolve("items/Flametongue.png"))
                .put(JadeStaff.class, texturePathResolver.resolve("items/JadeStaff.png"))
                .put(LongBow.class, texturePathResolver.resolve("items/LongBow.png"))
                .put(Hammer.class, texturePathResolver.resolve("effects/Hammer.png"))
                // resources
                .put(Wood.class, texturePathResolver.resolve("items/resource/Wood.png"))
                .build();

        mapTextures = ImmutableMap.<TileBase, Optional<Texture>>builder()
                .put(WorldMapTile.DIRT, texturePathResolver.resolve("tiles/DirtTileset.png"))
                .put(WorldMapTile.WATER, texturePathResolver.resolve("tiles/WaterTileset.png"))
                .put(WorldMapTile.GRASS, texturePathResolver.resolve("Terrain.png"))
                .put(Tile.STONE_WALL, texturePathResolver.resolve("wall.png"))
                .put(Tile.FLOOR, texturePathResolver.resolve("Terrain.png"))
                .put(Tile.EMPTY, texturePathResolver.resolve("void.png"))
                .build();

        objectAnimationTextures = ImmutableMap.<Class<? extends AnimatedObject>, Optional<Texture>>builder()
                .put(StandingTorch.class, texturePathResolver.resolve("object/StandingTorch.png"))
                .put(CampFire.class, texturePathResolver.resolve("object/CampFire.png"))
                .put(SpiderWeb.class, texturePathResolver.resolve("object/SpiderWeb.png"))
                .put(PracticeFigure.class, texturePathResolver.resolve("object/training/PracticeFigure.png"))
                .put(Grass.class, texturePathResolver.resolve("object/decoration/Grass.png"))
                .put(GrassV2.class, texturePathResolver.resolve("object/decoration/GrassV2.png"))
                .build();

        objectTextures = ImmutableMap.<Class<? extends WorldObject>, List<Optional<Texture>>>builder()
                .put(DungeonEntrance.class, Arrays.asList(texturePathResolver.resolve("object/DungeonEntrance.png")))
                .put(Ladder.class, Arrays.asList(texturePathResolver.resolve("object/Ladder.png")))
                .put(TreasureChest.class, Arrays.asList(texturePathResolver.resolve("object/TreasureChest.png"),
                        texturePathResolver.resolve("object/OpenTreasureChest.png")))

                // decoration
                .put(Tree.class, Arrays.asList(texturePathResolver.resolve("object/decoration/Tree.png")))
                .put(TreeV2.class, Arrays.asList(texturePathResolver.resolve("object/decoration/TreeV2.png")))
                .put(TreeV3.class, Arrays.asList(texturePathResolver.resolve("object/decoration/TreeV3.png")))
                .put(TreeV4.class, Arrays.asList(texturePathResolver.resolve("object/decoration/TreeV4.png")))
                .put(TreeV5.class, Arrays.asList(texturePathResolver.resolve("object/decoration/TreeV5.png")))
                .put(TreeV6.class, Arrays.asList(texturePathResolver.resolve("object/decoration/TreeV6.png")))
                .put(TreeV7.class, Arrays.asList(texturePathResolver.resolve("object/decoration/TreeV7.png")))
                .put(TreeV8.class, Arrays.asList(texturePathResolver.resolve("object/decoration/TreeV8.png")))
                .put(YellowFlower.class, Arrays.asList(texturePathResolver.resolve("object/decoration/YellowFlower.png")))
                .put(BlueFlower.class, Arrays.asList(texturePathResolver.resolve("object/decoration/BlueFlower.png")))
                .put(Bush.class, Arrays.asList(texturePathResolver.resolve("object/decoration/Bush.png")))
                .put(GiantLeafPlant.class, Arrays.asList(texturePathResolver.resolve("object/decoration/GiantLeafPlant.png")))
                .put(PineTree.class, Arrays.asList(texturePathResolver.resolve("object/decoration/PineTree.png")))
                .put(Rock.class, Arrays.asList(texturePathResolver.resolve("object/decoration/Rock.png")))
                .put(Log.class, Arrays.asList(texturePathResolver.resolve("object/decoration/Log.png")))

                // Wall
                .put(IncompleteWoodenWall.class, Arrays.asList(texturePathResolver.resolve("object/wall/WoodenWallTilesetIncomplete.png")))
                .put(IncompleteWoodenDoorWall.class, Arrays.asList(texturePathResolver.resolve("object/wall/WoodenWallDoorTilesetIncomplete.png")))
                .put(IncompleteWoodenFence.class, Arrays.asList(texturePathResolver.resolve("object/wall/IncompleteWoodenFence.png")))
                .put(WoodenWall.class, Arrays.asList(texturePathResolver.resolve("object/wall/WoodenWallTileset.png")))
                .put(WoodenFence.class, Arrays.asList(texturePathResolver.resolve("object/wall/WoodenFenceTileset.png")))
                .put(WoodenWallDoor.class, Arrays.asList(texturePathResolver.resolve("object/wall/WoodenWallDoorTileset.png")))

                // floor
                .put(WoodenFloor.class, Arrays.asList(texturePathResolver.resolve("object/floor/WoodenFloor.png")))
                .put(IncompleteWoodenFloor.class, Arrays.asList(texturePathResolver.resolve("object/floor/IncompleteWoodenFloor.png")))
                .put(IncompleteDirtRoad.class, Arrays.asList(texturePathResolver.resolve("object/floor/IncompleteRoadTile.png")))
                .put(Road.class, Arrays.asList(texturePathResolver.resolve("object/floor/RoadTile.png")))
                .put(StorageAreaFloor.class, Arrays.asList(texturePathResolver.resolve("object/floor/StorageArea.png")))
                .put(IncompleteStorageAreaFloor.class, Arrays.asList(texturePathResolver.resolve("object/floor/IncompleteStorageArea.png")))

                // furniture
                .put(IncompleteWoodenBed.class, Arrays.asList(texturePathResolver.resolve("object/furniture/IncompleteWoodenBed.png")))
                .put(WoodenBed.class, Arrays.asList(texturePathResolver.resolve("object/furniture/WoodenBed.png")))
                .put(BookCase.class, Arrays.asList(texturePathResolver.resolve("object/training/BookCase.png")))
                .put(ShootingTarget.class, Arrays.asList(texturePathResolver.resolve("object/training/ShootingTarget.png")))
                .put(Anvil.class, Arrays.asList(texturePathResolver.resolve("object/Anvil.png")))
                .put(IncompletePracticeFigure.class, Arrays.asList(texturePathResolver.resolve("object/furniture/IncompletePracticeFigure.png")))
                .put(IncompleteAnvil.class, Arrays.asList(texturePathResolver.resolve("object/furniture/IncompleteAnvil.png")))

                .build();

        dungeonTilesetTextures = ImmutableMap.<DungeonType, Optional<Texture>>builder()
                .put(DungeonType.CAVE, texturePathResolver.resolve("tiles/CaveTileset.png"))
                .put(DungeonType.ROOMS, texturePathResolver.resolve("tiles/RoomTileset.png"))
                .build();
    }

    public Texture getForActor(Class<? extends Actor> clazz) {
        return characterAnimationTextures.get(clazz).get();
    }

    public Texture getActionTexture(Class<? extends Action> clazz) {
        return actionTextures.get(clazz).get();
    }

    public Texture getFor(DungeonType dungeonType) {
        return dungeonTilesetTextures.get(dungeonType).get();
    }


    public Texture getFor(GuiComponent guiComponent) {
        return guiTextures.get(guiComponent).get();
    }

    public Texture getFor(LightSourceType lightSourceType) {
        return lightTextures.get(lightSourceType).get();
    }

    public List<Texture> getForobject(Class<? extends WorldObject> objectClass) {
        return objectTextures.get(objectClass)
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public Texture getForObjcetAnimation(Class<? extends AnimatedObject> clazz) {
        return objectAnimationTextures.get(clazz).get();
    }

    public Texture getForTile(TileBase tileBase) {
        return mapTextures.get(tileBase).get();
    }

    public Texture getFor(Class<? extends Item> clazz) {
        if (itemTextures.containsKey(clazz)) {
            return itemTextures.get(clazz).get();
        }
        return null;
    }

    public Texture getFor(MenuItem menuItem) {
        return menuItems.get(menuItem).get();
    }

    public void dispose() {
        for (Optional<Texture> t : itemTextures.values()) {
            t.get().dispose();
        }
    }

    public Texture getShadowTexture() {
        return shadowTexture.get();
    }
}
