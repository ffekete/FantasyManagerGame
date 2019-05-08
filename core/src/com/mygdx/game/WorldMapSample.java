package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.factory.ActorFactory;
import com.mygdx.game.actor.factory.Placement;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.monster.Goblin;
import com.mygdx.game.actor.monster.Skeleton;
import com.mygdx.game.common.SampleBase;
import com.mygdx.game.common.SampleInfo;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.creator.map.dungeon.CaveDungeonCreator;
import com.mygdx.game.creator.map.dungeon.MapGenerator;
import com.mygdx.game.creator.map.object.LinkedWorldObjectFactory;
import com.mygdx.game.creator.map.object.factory.ObjectPlacement;
import com.mygdx.game.creator.map.object.interactive.DungeonEntrance;
import com.mygdx.game.creator.map.worldmap.WorldMap;
import com.mygdx.game.creator.map.worldmap.WorldMapGenerator;
import com.mygdx.game.item.potion.SmallHealingPotion;
import com.mygdx.game.item.weapon.FlameTongue;
import com.mygdx.game.item.weapon.PoisonFang;
import com.mygdx.game.logic.GameLogicController;
import com.mygdx.game.logic.time.DayTimeCalculator;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.AnimationRegistry;
import com.mygdx.game.registry.ItemRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.renderer.RendererBatch;
import com.mygdx.game.renderer.camera.CameraPositionController;
import com.mygdx.game.utils.GdxUtils;


public class WorldMapSample extends SampleBase {

    public final static SampleInfo SAMPLE_INFO = new SampleInfo(WorldMapSample.class);

    private OrthographicCamera camera;
    private Viewport viewPort;
    private SpriteBatch spriteBatch;
    private TextureRegistry textureRegistry;

    Map2D worldMap;
    ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    ItemRegistry itemRegistry = ItemRegistry.INSTANCE;
    GameLogicController gameLogicController = new GameLogicController(actorRegistry);
    OrthographicCamera infoCamera;
    Viewport infoViewPort;
    BitmapFont bitmapFont;
    Actor hero;
    MapGenerator mapGenerator = new WorldMapGenerator();
    MapGenerator dungeonCreator = new CaveDungeonCreator();

    LinkedWorldObjectFactory objectFactory = LinkedWorldObjectFactory.INSTANCE;

    Map2D shownMap;

    @Override
    public void create() {

        infoCamera = new OrthographicCamera();
        infoViewPort = new FitViewport(1280, 720, infoCamera);
        bitmapFont = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
        //bitmapFont.getData().setScale(f);

        camera = new OrthographicCamera();
        viewPort = new FitViewport(100, 100, camera);
        spriteBatch = new SpriteBatch();

        worldMap = mapGenerator.create();
        textureRegistry = TextureRegistry.INSTANCE;
        Gdx.input.setInputProcessor(this);

        hero = ActorFactory.INSTANCE.create(Warrior.class, worldMap, Placement.FIXED.X(10).Y(10));
        hero.getInventory().add(new SmallHealingPotion());
        hero.getInventory().add(new SmallHealingPotion());
        hero.getInventory().add(new SmallHealingPotion());

        CameraPositionController.INSTANCE.focusOn(hero);

        Map2D dungeon = dungeonCreator.create();

        LinkedWorldObjectFactory.INSTANCE.create(DungeonEntrance.class, worldMap, dungeon, ObjectPlacement.FIXED.X(15).Y(15));

        ActorFactory.INSTANCE.create(Goblin.class, worldMap, Placement.RANDOM);
//        for (int i = 0; i < 15; i++) {
//            ActorFactory.INSTANCE.create(Skeleton.class, worldMap, Placement.RANDOM);
//        }

        MapRegistry.INSTANCE.add(worldMap);
        shownMap = worldMap;

        hero.setName("Adavark");

        PoisonFang poisonFang = new PoisonFang();
        poisonFang.setCoordinates(hero.getX() + 1, hero.getY());
        hero.setRightHandItem(new FlameTongue());

    }

    @Override
    public void render() {
        spriteBatch.setProjectionMatrix(camera.combined);
        viewPort.apply();
        spriteBatch.begin();

        GdxUtils.clearScreen();
        CameraPositionController.INSTANCE.updateCamera(camera, viewPort);
        gameLogicController.update();
        draw();

        spriteBatch.end();

        infoViewPort.apply();
        spriteBatch.setProjectionMatrix(infoCamera.combined);
        spriteBatch.begin();
        bitmapFont.draw(spriteBatch, "Hour: " + DayTimeCalculator.INSTANCE.getHour() + " Day: " + DayTimeCalculator.INSTANCE.getDay(), 10, 40);
        spriteBatch.end();
    }

    public void draw() {
        System.out.println(Gdx.graphics.getFramesPerSecond());

        if(Map2D.MapType.WORLD_MAP.equals(shownMap.getMapType()))
            RendererBatch.WORLD_MAP.draw(shownMap, spriteBatch);
        else
            RendererBatch.DUNGEON.draw(shownMap, spriteBatch);

        if (false) {
            // low fps test
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height, true);
        infoViewPort.update(width, height, true);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        textureRegistry.dispose();
        bitmapFont.dispose();
        AnimationRegistry.INSTANCE.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //dungeon = dungeonCreator.create();
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        float delta = Gdx.graphics.getDeltaTime();
        CameraPositionController.INSTANCE.updateZoomLevel(amount * delta);
        camera.update();
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        float delta = Gdx.graphics.getDeltaTime();

        if (keycode == Input.Keys.LEFT) {
            camera.position.x -= 20.0 * delta;
        }
        if (keycode == Input.Keys.RIGHT) {
            camera.position.x += 20.0 * delta;
        }
        if (keycode == Input.Keys.DOWN) {
            camera.position.y -= 20.0 * delta;
        }
        if (keycode == Input.Keys.UP) {
            camera.position.y += 20.0 * delta;
        }

        if (keycode == Input.Keys.F) {
            CameraPositionController.INSTANCE.focusOn(hero);
        }
        camera.update();

        return true;
    }
}
