package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.component.skill.WeaponSkill;
import com.mygdx.game.actor.factory.ActorFactory;
import com.mygdx.game.actor.factory.Placement;
import com.mygdx.game.actor.hero.Ranger;
import com.mygdx.game.actor.hero.Wizard;
import com.mygdx.game.actor.worker.Builder;
import com.mygdx.game.common.SampleBase;
import com.mygdx.game.common.SampleInfo;
import com.mygdx.game.item.potion.SmallAntiVenomPotion;
import com.mygdx.game.item.potion.SmallManaPotion;
import com.mygdx.game.item.weapon.bow.LongBow;
import com.mygdx.game.item.weapon.staff.JadeStaff;
import com.mygdx.game.logic.GameState;
import com.mygdx.game.logic.controller.GameFlowControllerFacade;
import com.mygdx.game.input.InputConfigurer;
import com.mygdx.game.input.keyboard.KeyboardInputControllerFacade;
import com.mygdx.game.input.mouse.MouseInputControllerFacade;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.cave.CaveDungeonCreator;
import com.mygdx.game.map.dungeon.MapGenerator;
import com.mygdx.game.map.dungeon.factory.DungeonFactory;
import com.mygdx.game.map.dungeon.room.DungeonWithRoomsCreator;
import com.mygdx.game.object.LinkedWorldObjectFactory;
import com.mygdx.game.object.decoration.Tree;
import com.mygdx.game.object.factory.ObjectFactory;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.object.interactive.DungeonEntrance;
import com.mygdx.game.map.worldmap.WorldMapGenerator;
import com.mygdx.game.item.potion.SmallHealingPotion;
import com.mygdx.game.logic.controller.SandboxGameLogicController;
import com.mygdx.game.logic.time.DayTimeCalculator;
import com.mygdx.game.registry.*;
import com.mygdx.game.registry.RendererToolsRegistry;
import com.mygdx.game.renderer.RenderingFacade;
import com.mygdx.game.renderer.sandbox.InfoScreenRenderer;
import com.mygdx.game.renderer.camera.CameraPositionController;
import com.mygdx.game.utils.GdxUtils;


public class WorldMapSample extends SampleBase {

    public final static SampleInfo SAMPLE_INFO = new SampleInfo(WorldMapSample.class);

    private OrthographicCamera camera;

    Map2D worldMap;
    Map2D dungeon;
    Map2D dungeon2;
    SandboxGameLogicController sandboxGameLogicController = SandboxGameLogicController.INSTANCE;
    OrthographicCamera infoCamera;
    Viewport infoViewPort;
    BitmapFont bitmapFont;
    Actor hero;
    Actor ranger;
    Actor builder;

    MapGenerator mapGenerator = new WorldMapGenerator();
    DungeonFactory dungeonFactory = DungeonFactory.INSTANCE;

    ShapeRenderer shapeRenderer;

    ImageButton buildButton;
    Stage sandboxStage;
    Stage builderStage;
    Stage inventoryStage;

    @Override
    public void create() {

        sandboxStage = new Stage();
        builderStage = new Stage();
        inventoryStage = new Stage();

        shapeRenderer = new ShapeRenderer();
        infoCamera = new OrthographicCamera();
        infoViewPort = new FitViewport(Config.Screen.CANVAS_WIDTH, Config.Screen.HEIGHT, infoCamera);
        bitmapFont = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));

        Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildButton.png"))));
        Drawable drawable2 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildButtonDown.png"))));

        configureButtons(drawable, drawable2);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(60, 60 * (h / w));

        RendererToolsRegistry.INSTANCE.setSpriteBatch(new SpriteBatch());
        RendererToolsRegistry.INSTANCE.setBitmapFont(bitmapFont);
        RendererToolsRegistry.INSTANCE.setShapeRenderer(shapeRenderer);
        RendererToolsRegistry.INSTANCE.setCamera(camera);
        RendererToolsRegistry.INSTANCE.setInfoCamera(infoCamera);
        RendererToolsRegistry.INSTANCE.addStage(GameState.Sandbox, sandboxStage);
        RendererToolsRegistry.INSTANCE.addStage(GameState.Builder, builderStage);
        RendererToolsRegistry.INSTANCE.addStage(GameState.Inventory, inventoryStage);
        RendererToolsRegistry.INSTANCE.setInfoViewPort(infoViewPort);

        worldMap = mapGenerator.create(0);

        InputConfigurer.INSTANCE.setInputProcessor(sandboxStage, this);

        builder = ActorFactory.INSTANCE.create(Builder.class, worldMap, Placement.FIXED.X(7).Y(10));

        ranger = ActorFactory.INSTANCE.create(Ranger.class, worldMap, Placement.FIXED.X(8).Y(10));
        ranger.equip(new LongBow());
        ranger.setName("Aragorn");
        ranger.getWeaponSkills().put(WeaponSkill.Bow, 5);
        ranger.getInventory().add(new SmallHealingPotion());
        ranger.getInventory().add(new SmallHealingPotion());
        ranger.getInventory().add(new SmallHealingPotion());

        hero = ActorFactory.INSTANCE.create(Wizard.class, worldMap, Placement.FIXED.X(9).Y(10));
        hero.getInventory().add(new SmallHealingPotion());
        hero.getInventory().add(new SmallHealingPotion());
        hero.getInventory().add(new SmallHealingPotion());

        hero.getInventory().add(new SmallManaPotion());
        hero.getInventory().add(new SmallManaPotion());
        hero.getInventory().add(new SmallManaPotion());

        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());
        hero.getInventory().add(new SmallAntiVenomPotion());

        //CameraPositionController.INSTANCE.focusOn(hero);

        dungeon = dungeonFactory.create(CaveDungeonCreator.class);
        dungeon2 = dungeonFactory.create(DungeonWithRoomsCreator.class);

        LinkedWorldObjectFactory.INSTANCE.create(DungeonEntrance.class, worldMap, dungeon, ObjectPlacement.FIXED.X(15).Y(15), ObjectPlacement.RANDOM);
        LinkedWorldObjectFactory.INSTANCE.create(DungeonEntrance.class, worldMap, dungeon2, ObjectPlacement.FIXED.X(13).Y(13), ObjectPlacement.RANDOM);

        ObjectFactory.create(Tree.class, worldMap, ObjectPlacement.FIXED.X(10).Y(10));
        ObjectFactory.create(Tree.class, worldMap, ObjectPlacement.FIXED.X(11).Y(10));
        ObjectFactory.create(Tree.class, worldMap, ObjectPlacement.FIXED.X(10).Y(11));

        MapRegistry.INSTANCE.add(worldMap);
        MapRegistry.INSTANCE.add(dungeon);
        MapRegistry.INSTANCE.add(dungeon2);
        MapRegistry.INSTANCE.setCurrentMapToShow(worldMap);

        hero.setName("Adavark");

        hero.equip(new JadeStaff());

        MapRegistry.INSTANCE.setCurrentMapToShow(worldMap);

    }

    private void configureButtons(Drawable drawable, Drawable drawable2) {
        sandboxStage.addActor(getBuildButton(drawable, drawable2));
        builderStage.addActor(getBuildButton(drawable, drawable2));
    }

    private ImageButton getBuildButton(Drawable drawable, Drawable drawable2) {
        ImageButton buildButton = new ImageButton(drawable, drawable2);
        buildButton.setPosition(-20, 30);

        //Table myTable = new Table();
        //myTable.setFillParent(true); // <-- sets initial table to fill it's parent (in this case the sandboxStage)
        //ScrollPane myScrollpane = new ScrollPane(buildButton); // <-- Add actors to hold by scrollpane

        // myTable.add(myScrollpane);
        //sandboxStage.addActor(myTable);

        buildButton.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameFlowControllerFacade.INSTANCE.toggleGameState();
                return true;
            }

        });
        return buildButton;
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
        camera.viewportWidth = 60;
        camera.viewportHeight = 60f * height / width;
        camera.update();
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
