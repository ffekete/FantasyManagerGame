package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.component.skill.WeaponSkill;
import com.mygdx.game.actor.component.trait.Trait;
import com.mygdx.game.actor.factory.ActorFactory;
import com.mygdx.game.actor.factory.Placement;
import com.mygdx.game.actor.hero.Ranger;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.hero.Wizard;
import com.mygdx.game.actor.wildlife.Rabbit;
import com.mygdx.game.actor.wildlife.Wolf;
import com.mygdx.game.actor.worker.Builder;
import com.mygdx.game.actor.worker.Shopkeeper;
import com.mygdx.game.actor.worker.Smith;
import com.mygdx.game.common.SampleBase;
import com.mygdx.game.common.SampleInfo;
import com.mygdx.game.input.InputConfigurer;
import com.mygdx.game.input.keyboard.KeyboardInputControllerFacade;
import com.mygdx.game.input.mouse.MouseInputControllerFacade;
import com.mygdx.game.item.armor.LeatherArmor;
import com.mygdx.game.item.food.Bread;
import com.mygdx.game.item.potion.SmallAntiVenomPotion;
import com.mygdx.game.item.potion.SmallHealingPotion;
import com.mygdx.game.item.potion.SmallManaPotion;
import com.mygdx.game.item.shield.MediumShield;
import com.mygdx.game.item.weapon.bow.LongBow;
import com.mygdx.game.item.weapon.staff.JadeStaff;
import com.mygdx.game.item.weapon.sword.ShortSwordPlusFour;
import com.mygdx.game.logic.GameState;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.controller.GameFlowControllerFacade;
import com.mygdx.game.logic.controller.SandboxGameLogicController;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.MapGenerator;
import com.mygdx.game.map.dungeon.cave.CaveDungeonCreator;
import com.mygdx.game.map.dungeon.factory.DungeonFactory;
import com.mygdx.game.map.dungeon.factory.WorldMapFactory;
import com.mygdx.game.map.dungeon.room.DungeonWithRoomsCreator;
import com.mygdx.game.map.worldmap.*;
import com.mygdx.game.object.LinkedWorldObjectFactory;
import com.mygdx.game.object.decoration.BlueFlower;
import com.mygdx.game.object.decoration.Bush;
import com.mygdx.game.object.decoration.Tree;
import com.mygdx.game.object.decoration.YellowFlower;
import com.mygdx.game.object.factory.HouseFactory;
import com.mygdx.game.object.factory.ObjectFactory;
import com.mygdx.game.object.interactive.DungeonEntrance;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.RendererToolsRegistry;
import com.mygdx.game.registry.TownDataRegistry;
import com.mygdx.game.renderer.RenderingFacade;
import com.mygdx.game.renderer.camera.CameraPositionController;
import com.mygdx.game.stage.StageConfigurer;


public class WorldMapSample extends SampleBase {

    public final static SampleInfo SAMPLE_INFO = new SampleInfo(WorldMapSample.class);

    private OrthographicCamera camera;

    WorldMap worldMap;
    OrthographicCamera infoCamera;
    Viewport infoViewPort;
    Viewport viewport;
    BitmapFont bitmapFont;
    BitmapFont bitmapFontSmall;
    BitmapFont bitmapFontSmallest;
    Actor hero;
    Actor ranger;
    Actor warrior;
    Actor builder;
    Actor smith;
    Actor shopkeeper;

    MapGenerator<WorldMap> mapGenerator = new WorldMapGenerator();

    ShapeRenderer shapeRenderer;

    Stage sandboxStage;
    Stage builderStage;
    Stage inventoryStage;
    private BitmapFont bitmapFontTiny;

    @Override
    public void create() {

        sandboxStage = new Stage();
        builderStage = new Stage();
        inventoryStage = new Stage();

        shapeRenderer = new ShapeRenderer();
        infoCamera = new OrthographicCamera();
        infoViewPort = new StretchViewport(Config.Screen.WIDTH, Config.Screen.HEIGHT, infoCamera);
        bitmapFont = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
        bitmapFontSmall = new BitmapFont(Gdx.files.internal("fonts/font25.fnt"));
        bitmapFontSmallest = new BitmapFont(Gdx.files.internal("fonts/font15.fnt"));
        bitmapFontTiny = new BitmapFont(Gdx.files.internal("fonts/font10.fnt"));


        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(60f, 60f * (h / w));
        viewport = new FitViewport(60, 40, camera);
        viewport.apply();

        RendererToolsRegistry.INSTANCE.setSpriteBatch(new SpriteBatch());
        RendererToolsRegistry.INSTANCE.setBitmapFont(bitmapFont);
        RendererToolsRegistry.INSTANCE.setShapeRenderer(shapeRenderer);
        RendererToolsRegistry.INSTANCE.setCamera(camera);
        RendererToolsRegistry.INSTANCE.setInfoCamera(infoCamera);
        RendererToolsRegistry.INSTANCE.setInfoViewPort(infoViewPort);
        RendererToolsRegistry.INSTANCE.setBitmapFontSmall(bitmapFontSmall);
        RendererToolsRegistry.INSTANCE.setBitmapFontTiny(bitmapFontTiny);
        RendererToolsRegistry.INSTANCE.setBitmapFontSmallest(bitmapFontSmallest);

        StageConfigurer.INSTANCE.configureButtons();

        worldMap = WorldMapFactory.INSTANCE.create();

        MapRegistry.INSTANCE.setWorldMap(worldMap);

        InputConfigurer.INSTANCE.setInputProcessor(StageConfigurer.INSTANCE.getFor(GameState.Sandbox), StageConfigurer.INSTANCE.getFor(GameState.Builder),  this);

        builder = ActorFactory.INSTANCE.create(Builder.class, worldMap, Placement.FIXED.X(7).Y(10));

        smith = ActorFactory.INSTANCE.create(Smith.class, worldMap, Placement.FIXED.X(8).Y(10));

        shopkeeper = ActorFactory.INSTANCE.create(Shopkeeper.class, worldMap, Placement.FIXED.X(6).Y(5));

        warrior = ActorFactory.INSTANCE.create(Warrior.class, worldMap, Placement.FIXED.X(8).Y(11));
        warrior.equip(new ShortSwordPlusFour());
        warrior.equip(new MediumShield());
        warrior.setName("Boromir");
        warrior.addTrait(Trait.Friendly);
        warrior.getInventory().add(new SmallAntiVenomPotion());
        warrior.getInventory().add(new Bread());
        warrior.getInventory().add(new Bread());
        warrior.getInventory().add(new Bread());
        warrior.getInventory().add(new Bread());
        warrior.getInventory().add(new Bread());
        warrior.getInventory().add(new LeatherArmor());
        //warrior.increaseSleepiness(49000);
        warrior.increaseTrainingNeeds(49000);

        ranger = ActorFactory.INSTANCE.create(Ranger.class, worldMap, Placement.FIXED.X(8).Y(10));
        ranger.equip(new LongBow());
        ranger.setName("Aragorn");
        ranger.getWeaponSkills().put(WeaponSkill.Bow, 5);
        ranger.getInventory().add(new SmallHealingPotion());
        ranger.getInventory().add(new SmallHealingPotion());
        ranger.getInventory().add(new SmallHealingPotion());

//        ranger.getInventory().add(new Bread());
//        ranger.getInventory().add(new Bread());
//        ranger.getInventory().add(new Bread());
//        ranger.getInventory().add(new Bread());
        ranger.increaseSleepiness(10000);
        ranger.increaseTrainingNeeds(48000);
        ranger.increaseHunger(98000);

        hero = ActorFactory.INSTANCE.create(Wizard.class, worldMap, Placement.FIXED.X(3).Y(3));
        hero.getInventory().add(new SmallHealingPotion());
        hero.getInventory().add(new SmallHealingPotion());
        hero.getInventory().add(new SmallHealingPotion());

        hero.getInventory().add(new SmallManaPotion());
        hero.getInventory().add(new SmallManaPotion());
        hero.getInventory().add(new SmallManaPotion());

        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());

        hero.getInventory().add(new Bread());
        hero.getInventory().add(new Bread());
        hero.getInventory().add(new Bread());

        hero.increaseTrainingNeeds(48000);

        hero.increaseSleepiness(10000);

        //hero.addExperiencePoints(100000);

        hero.setName("Gandalf");

        hero.equip(new JadeStaff());
        hero.getInventory().add(new LeatherArmor());

        smith.setName("Will");

        MapRegistry.INSTANCE.setCurrentMapToShow(worldMap);


       //HouseFactory.INSTANCE.create(7,1, 3, worldMap);

        HouseFactory.INSTANCE.create(0,0, 5, worldMap);

        //HouseFactory.INSTANCE.create(15, 1, 3, worldMap);

        //HouseFactory.INSTANCE.create(1, 8, 3, worldMap);

        //HouseFactory.INSTANCE.create(1, 13, 3, worldMap);

        Actor wolf = ActorFactory.INSTANCE.create(Wolf.class, worldMap, Placement.FIXED.X(11).Y(11));
        Actor rabbit = ActorFactory.INSTANCE.create(Rabbit.class, worldMap, Placement.FIXED.X(13).Y(11));
        wolf.increaseSleepiness(48000);

        Map2D m = DungeonFactory.INSTANCE.create(DungeonWithRoomsCreator.class);
        LinkedWorldObjectFactory.INSTANCE.create(DungeonEntrance.class, worldMap, m, ObjectPlacement.FIXED.X(15).Y(15), ObjectPlacement.RANDOM);

        m.getVisibilityCalculator().calculateFor(m.getDefaultSpawnPoint(), 3, m, false);

        new RiverCreator().connect(worldMap, Point.of(15, 18) , Point.of(20, 25));
    }

    @Override
    public void render() {
        GameFlowControllerFacade.INSTANCE.update();

        long start = System.currentTimeMillis();

        RenderingFacade.INSTANCE.draw();

        if (Config.SHOW_ELAPSED_TIME_IN_RENDERER) {
            System.out.println("elapsed time in rendering: " + (System.currentTimeMillis() - start));
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 60f;
        camera.viewportHeight = 60f * height / width;
        //camera.update();
        viewport.update(60, 40, false);
        infoViewPort.update(width, height, true);
        infoCamera.update();
    }

    @Override
    public void dispose() {
//        RendererToolsRegistry.INSTANCE.getSpriteBatch().dispose();
//        textureRegistry.dispose();
//        bitmapFont.dispose();
//        AnimationRegistry.INSTANCE.dispose();
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return MouseInputControllerFacade.INSTANCE.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return MouseInputControllerFacade.INSTANCE.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean scrolled(int amount) {
        float delta = Gdx.graphics.getRawDeltaTime();
        CameraPositionController.INSTANCE.updateZoomLevel(amount * delta);
        camera.update();
        return true;
    }



    @Override
    public boolean keyDown(int keycode) {
        return KeyboardInputControllerFacade.INSTANCE.processInput(keycode, camera);
    }
}
