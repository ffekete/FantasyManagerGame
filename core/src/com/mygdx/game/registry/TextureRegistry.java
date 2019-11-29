package com.mygdx.game.registry;

import com.badlogic.gdx.graphics.Texture;
import com.google.common.collect.ImmutableMap;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Gender;
import com.mygdx.game.actor.hero.Ranger;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.hero.Wizard;
import com.mygdx.game.actor.monster.*;
import com.mygdx.game.actor.wildlife.Rabbit;
import com.mygdx.game.actor.wildlife.Wolf;
import com.mygdx.game.actor.worker.Builder;
import com.mygdx.game.actor.worker.Shopkeeper;
import com.mygdx.game.actor.worker.Smith;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.armor.ChainMailArmor;
import com.mygdx.game.item.armor.LeatherArmor;
import com.mygdx.game.item.armor.PlateMailArmor;
import com.mygdx.game.item.buildertool.Hammer;
import com.mygdx.game.item.component.WolfPelt;
import com.mygdx.game.item.food.Bread;
import com.mygdx.game.item.food.RabbitMeat;
import com.mygdx.game.item.money.MoneyBag;
import com.mygdx.game.item.potion.SmallAntiVenomPotion;
import com.mygdx.game.item.potion.SmallHealingPotion;
import com.mygdx.game.item.potion.SmallManaPotion;
import com.mygdx.game.item.resources.Wood;
import com.mygdx.game.item.shield.MediumShield;
import com.mygdx.game.item.shield.SmallShiled;
import com.mygdx.game.item.weapon.bow.LongBow;
import com.mygdx.game.item.weapon.staff.JadeStaff;
import com.mygdx.game.item.weapon.sword.*;
import com.mygdx.game.item.weapon.twohandedsword.Flamberge;
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
import com.mygdx.game.object.furniture.*;
import com.mygdx.game.object.interactive.*;
import com.mygdx.game.object.light.LightSourceType;
import com.mygdx.game.object.wall.*;
import com.mygdx.game.renderer.gui.component.GuiComponent;
import com.mygdx.game.resolver.ModdablePathResolver;
import com.mygdx.game.resolver.PathResolver;

import java.util.*;
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

    private Map<String, Map<Gender, Map<String, ArrayList<Texture>>>> bodyPartTextures;
    private Map<String, Map<Gender, Map<String, Texture>>> armorAppearanceTextures;

    public TextureRegistry(PathResolver<Texture> texturePathResolver) {

        bodyPartTextures = new HashMap<>();
        armorAppearanceTextures = new HashMap<>();


        AnimationRegistry.INSTANCE.getAnimationSet().getAnimationSets()
                .stream().forEach(archetype -> {
            AnimationRegistry.INSTANCE.getAnimationSet().getAnimationSets().forEach(a -> {

                for(Gender gender : new Gender[]{Gender.Male, Gender.Female}) {
                    bodyPartTextures.computeIfAbsent(a.getType(), v -> new HashMap<>());

                    bodyPartTextures.get(a.getType()).computeIfAbsent(gender, v -> new HashMap<>());
                    bodyPartTextures.get(a.getType()).computeIfAbsent(gender, v -> new HashMap<>());
                    bodyPartTextures.get(a.getType()).computeIfAbsent(gender, v -> new HashMap<>());
                    bodyPartTextures.get(a.getType()).computeIfAbsent(gender, v -> new HashMap<>());


                    bodyPartTextures.get(a.getType()).get(gender).computeIfAbsent("body", v -> new ArrayList<>());
                    bodyPartTextures.get(a.getType()).get(gender).computeIfAbsent("eyes", v -> new ArrayList<>());
                    bodyPartTextures.get(a.getType()).get(gender).computeIfAbsent("hair", v -> new ArrayList<>());
                    bodyPartTextures.get(a.getType()).get(gender).computeIfAbsent("beard", v -> new ArrayList<>());

                    armorAppearanceTextures.computeIfAbsent(a.getType(), v -> new HashMap<>());
                    armorAppearanceTextures.computeIfAbsent(a.getType(), v -> new HashMap<>());

                    armorAppearanceTextures.get(a.getType()).computeIfAbsent(gender, v -> new HashMap<>());
                    armorAppearanceTextures.get(a.getType()).computeIfAbsent(gender, v -> new HashMap<>());

                    if (a.getRacialAnimations().get(gender).getArmor() != null) {
                        a.getRacialAnimations().get(gender).getArmor().entrySet().stream()
                                .forEach(entry -> {
                                    armorAppearanceTextures.get(a.getType()).get(gender).put(entry.getKey(), texturePathResolver.resolve(entry.getValue()).get());
                                });
                    }

                    if (a.getRacialAnimations().get(gender).getBeard() != null) {
                        a.getRacialAnimations().get(gender).getBeard().getParts().forEach(beard -> {
                            bodyPartTextures.get(a.getType()).get(gender).get("beard").add(texturePathResolver.resolve(beard).get());
                        });
                    }

                    a.getRacialAnimations().get(gender).getBody().getParts().forEach(body -> {
                        bodyPartTextures.get(a.getType()).get(gender).get("body").add(texturePathResolver.resolve(body).get());
                    });

                    a.getRacialAnimations().get(gender).getEyes().getParts().forEach(eyes -> {
                        bodyPartTextures.get(a.getType()).get(gender).get("eyes").add(texturePathResolver.resolve(eyes).get());
                    });

                    a.getRacialAnimations().get(gender).getHair().getParts().forEach(hair -> {
                        bodyPartTextures.get(a.getType()).get(gender).get("hair").add(texturePathResolver.resolve(hair).get());
                    });
                }
            });

        });

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
                .put(Shopkeeper.class, texturePathResolver.resolve("actors/worker/Shopkeeper.png"))

                // Wildlife
                .put(Wolf.class, texturePathResolver.resolve("actors/wildlife/Wolf.png"))
                .put(Rabbit.class, texturePathResolver.resolve("actors/wildlife/Rabbit.png"))

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
                .put(LocationMarkerAction.class, texturePathResolver.resolve("ui/marker/LocationMarker.png"))
                .build();

        guiTextures = ImmutableMap.<GuiComponent, Optional<Texture>>builder()
                .put(GuiComponent.HUD, texturePathResolver.resolve("Hud.png"))
                .build();

        lightTextures = ImmutableMap.<LightSourceType, Optional<Texture>>builder()
                .put(LightSourceType.Ambient, texturePathResolver.resolve("light_bu.png"))
                .put(LightSourceType.Beam, texturePathResolver.resolve("mist.jpg"))
                .build();

        itemTextures = ImmutableMap.<Class, Optional<Texture>>builder()
                .put(ShortSword.class, texturePathResolver.resolve("items/weapons/ShortSword.png"))
                .put(ShortSwordPlusOne.class, texturePathResolver.resolve("items/weapons/ShortSword.png"))
                .put(ShortSwordPlusFour.class, texturePathResolver.resolve("items/weapons/ShortSword.png"))
                .put(SmallHealingPotion.class, texturePathResolver.resolve("items/HealingPotion.png"))
                .put(SmallManaPotion.class, texturePathResolver.resolve("items/ManaPotion.png"))
                .put(SmallAntiVenomPotion.class, texturePathResolver.resolve("items/AntiVenom.png"))
                .put(SmallShiled.class, texturePathResolver.resolve("items/SmallShield.png"))
                .put(MediumShield.class, texturePathResolver.resolve("items/MediumShield.png"))
                .put(PoisonFang.class, texturePathResolver.resolve("items/weapons/PoisonFang.png"))
                .put(FlameTongue.class, texturePathResolver.resolve("items/weapons/Flametongue.png"))
                .put(JadeStaff.class, texturePathResolver.resolve("items/weapons/JadeStaff.png"))
                .put(LongBow.class, texturePathResolver.resolve("items/weapons/LongBow.png"))
                .put(Flamberge.class, texturePathResolver.resolve("items/weapons/Flamberge.png"))
                .put(Hammer.class, texturePathResolver.resolve("effects/Hammer.png"))

                // armor
                .put(LeatherArmor.class, texturePathResolver.resolve("items/armors/LeatherArmor.png"))
                .put(ChainMailArmor.class, texturePathResolver.resolve("items/armors/ChainMailArmor.png"))
                .put(PlateMailArmor.class, texturePathResolver.resolve("items/armors/PlateMailArmor.png"))

                // components
                .put(WolfPelt.class, texturePathResolver.resolve("items/components/WolfPelt.png"))

                // food
                .put(Bread.class, texturePathResolver.resolve("items/food/Bread.png"))
                .put(RabbitMeat.class, texturePathResolver.resolve("items/food/RabbitMeat.png"))

                // money
                .put(MoneyBag.class, texturePathResolver.resolve("items/MoneyBag.png"))

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
                .put(Smelter.class, texturePathResolver.resolve("object/crafting/Smelter.png"))
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
                .put(TreeV9.class, Arrays.asList(texturePathResolver.resolve("object/decoration/TreeV9.png")))
                .put(TreeV10.class, Arrays.asList(texturePathResolver.resolve("object/decoration/TreeV10.png")))
                .put(Pond.class, Arrays.asList(texturePathResolver.resolve("object/decoration/Pond.png")))
                .put(YellowFlower.class, Arrays.asList(texturePathResolver.resolve("object/decoration/YellowFlower.png")))
                .put(BlueFlower.class, Arrays.asList(texturePathResolver.resolve("object/decoration/BlueFlower.png")))
                .put(Bush.class, Arrays.asList(texturePathResolver.resolve("object/decoration/Bush.png")))
                .put(GiantLeafPlant.class, Arrays.asList(texturePathResolver.resolve("object/decoration/GiantLeafPlant.png")))
                .put(PineTree.class, Arrays.asList(texturePathResolver.resolve("object/decoration/PineTree.png")))
                .put(Rock.class, Arrays.asList(texturePathResolver.resolve("object/decoration/Rock.png")))
                .put(Log.class, Arrays.asList(texturePathResolver.resolve("object/decoration/Log.png")))

                // River
                .put(River.class, Arrays.asList(texturePathResolver.resolve("object/decoration/RiverTile.png")))

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
                .put(DirtRoad.class, Arrays.asList(texturePathResolver.resolve("object/floor/RoadTile.png")))
                .put(StorageAreaFloor.class, Arrays.asList(texturePathResolver.resolve("object/floor/StorageArea.png")))
                .put(IncompleteStorageAreaFloor.class, Arrays.asList(texturePathResolver.resolve("object/floor/IncompleteStorageArea.png")))

                // furniture
                .put(IncompleteWoodenBed.class, Arrays.asList(texturePathResolver.resolve("object/furniture/IncompleteWoodenBed.png")))
                .put(WoodenBed.class, Arrays.asList(texturePathResolver.resolve("object/furniture/WoodenBed.png")))
                .put(BookCase.class, Arrays.asList(texturePathResolver.resolve("object/training/BookCase.png")))
                .put(ShootingTarget.class, Arrays.asList(texturePathResolver.resolve("object/training/ShootingTarget.png")))
                .put(Anvil.class, Arrays.asList(texturePathResolver.resolve("object/crafting/Anvil.png")))
                .put(IncompletePracticeFigure.class, Arrays.asList(texturePathResolver.resolve("object/furniture/IncompletePracticeFigure.png")))
                .put(IncompleteAnvil.class, Arrays.asList(texturePathResolver.resolve("object/crafting/IncompleteAnvil.png")))
                .put(IncompleteSmelter.class, Arrays.asList(texturePathResolver.resolve("object/crafting/IncompleteSmelter.png")))
                .put(IncompleteShopkeepersDesk.class, Arrays.asList(texturePathResolver.resolve("object/furniture/IncompleteShopkeeperDeskTile.png")))
                .put(ShopkeepersDesk.class, Arrays.asList(texturePathResolver.resolve("object/furniture/ShopkeeperDeskTile.png")))

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

    public Texture getBody(String archeType, String bodyPart, int index, Gender gender) {
        return bodyPartTextures.get(archeType).get(gender).get(bodyPart).get(index);
    }

    public Texture getArmor(String archeType, String armorType, Gender gender) {
        return armorAppearanceTextures.get(archeType).get(gender).get(armorType);
    }
}
