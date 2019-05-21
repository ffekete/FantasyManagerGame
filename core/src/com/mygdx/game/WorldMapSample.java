package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.factory.ActorFactory;
import com.mygdx.game.actor.factory.Placement;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.monster.Goblin;
import com.mygdx.game.common.SampleBase;
import com.mygdx.game.common.SampleInfo;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.creator.map.dungeon.CaveDungeonCreator;
import com.mygdx.game.creator.map.dungeon.MapGenerator;
import com.mygdx.game.object.LinkedWorldObjectFactory;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.object.interactive.DungeonEntrance;
import com.mygdx.game.creator.map.worldmap.WorldMapGenerator;
import com.mygdx.game.item.potion.SmallHealingPotion;
import com.mygdx.game.item.weapon.sword.FlameTongue;
import com.mygdx.game.item.weapon.sword.PoisonFang;
import com.mygdx.game.logic.GameLogicController;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.time.DayTimeCalculator;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.AnimationRegistry;
import com.mygdx.game.registry.ItemRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.SpriteBatchRegistry;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.renderer.RendererBatch;
import com.mygdx.game.renderer.camera.CameraPositionController;
import com.mygdx.game.utils.GdxUtils;

import java.util.Random;


public class WorldMapSample extends SampleBase {

    public final static SampleInfo SAMPLE_INFO = new SampleInfo(WorldMapSample.class);

    private OrthographicCamera camera;
    private TextureRegistry textureRegistry;

    Map2D worldMap;
    Map2D dungeon;
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

    ShapeRenderer shapeRenderer;

    @Override
    public void create() {

        infoCamera = new OrthographicCamera();
        infoViewPort = new FitViewport(Config.Screen.CANVAS_WIDTH, Config.Screen.HEIGHT, infoCamera);
        bitmapFont = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(60, 60 * (h / w));
        SpriteBatchRegistry.INSTANCE.setSpriteBatch(new SpriteBatch());

        worldMap = mapGenerator.create();
        textureRegistry = TextureRegistry.INSTANCE;
        Gdx.input.setInputProcessor(this);

        hero = ActorFactory.INSTANCE.create(Warrior.class, worldMap, Placement.FIXED.X(10).Y(10));
        hero.getInventory().add(new SmallHealingPotion());
        hero.getInventory().add(new SmallHealingPotion());
        hero.getInventory().add(new SmallHealingPotion());

        CameraPositionController.INSTANCE.focusOn(hero);

        dungeon = dungeonCreator.create();
        int x = 0,y = 0;
        do {
            x = new Random().nextInt(dungeon.getWidth());
            y = new Random().nextInt(dungeon.getHeight());

        } while(dungeon.getTile(x,y).isObstacle());

        dungeon.setDefaultSpawningPoint(Point.of(x, y));

        LinkedWorldObjectFactory.INSTANCE.create(DungeonEntrance.class, worldMap, dungeon, ObjectPlacement.FIXED.X(15).Y(15));

        ActorFactory.INSTANCE.create(Goblin.class, worldMap, Placement.RANDOM);
//        for (int i = 0; i < 15; i++) {
//            ActorFactory.INSTANCE.create(Skeleton.class, dungeon, Placement.RANDOM);
//        }

        MapRegistry.INSTANCE.add(worldMap);
        MapRegistry.INSTANCE.add(dungeon);
        MapRegistry.INSTANCE.setCurrentMapToShow(worldMap);

        hero.setName("Adavark");

        PoisonFang poisonFang = new PoisonFang();
        poisonFang.setCoordinates(hero.getX() + 1, hero.getY());
        hero.setRightHandItem(new FlameTongue());

        MapRegistry.INSTANCE.setCurrentMapToShow(worldMap);

        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render() {
        SpriteBatchRegistry.INSTANCE.getSpriteBatch().setProjectionMatrix(camera.combined);
        SpriteBatchRegistry.INSTANCE.getSpriteBatch().begin();

        GdxUtils.clearScreen();
        CameraPositionController.INSTANCE.updateCamera(camera);
        gameLogicController.update();
        draw();

        SpriteBatchRegistry.INSTANCE.getSpriteBatch().end();

        infoViewPort.apply();
        SpriteBatchRegistry.INSTANCE.getSpriteBatch().setProjectionMatrix(infoCamera.combined);
        SpriteBatchRegistry.INSTANCE.getSpriteBatch().begin();
        bitmapFont.draw(SpriteBatchRegistry.INSTANCE.getSpriteBatch(), "Hour: " + DayTimeCalculator.INSTANCE.getHour() + " Day: " + DayTimeCalculator.INSTANCE.getDay(), 10, 40);
        SpriteBatchRegistry.INSTANCE.getSpriteBatch().end();
    }

    public void draw() {
        //System.out.println(Gdx.graphics.getFramesPerSecond());

        if(Map2D.MapType.WORLD_MAP.equals(MapRegistry.INSTANCE.getCurrentMapToShow().getMapType()))
            RendererBatch.WORLD_MAP.draw(MapRegistry.INSTANCE.getCurrentMapToShow(), SpriteBatchRegistry.INSTANCE.getSpriteBatch());
        else
            RendererBatch.DUNGEON.draw(MapRegistry.INSTANCE.getCurrentMapToShow(), SpriteBatchRegistry.INSTANCE.getSpriteBatch());

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
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
        infoViewPort.update(width, height, true);
        infoCamera.update();
    }

    @Override
    public void dispose() {
        SpriteBatchRegistry.INSTANCE.getSpriteBatch().dispose();
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

        if(keycode == Input.Keys.SPACE) {
            if(MapRegistry.INSTANCE.getCurrentMapToShow() == worldMap) {
                MapRegistry.INSTANCE.setCurrentMapToShow(dungeon);
            } else {
                MapRegistry.INSTANCE.setCurrentMapToShow(worldMap);
            }
        }

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
