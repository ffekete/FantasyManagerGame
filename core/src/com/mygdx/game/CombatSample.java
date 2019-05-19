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
import com.mygdx.game.actor.monster.Skeleton;
import com.mygdx.game.common.SampleBase;
import com.mygdx.game.common.SampleInfo;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.creator.map.dungeon.CaveDungeonCreator;
import com.mygdx.game.creator.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.creator.map.dungeon.MapGenerator;
import com.mygdx.game.item.food.Bread;
import com.mygdx.game.item.potion.SmallHealingPotion;
import com.mygdx.game.item.shield.Shield;
import com.mygdx.game.item.shield.SmallShiled;
import com.mygdx.game.item.weapon.FlameTongue;
import com.mygdx.game.item.weapon.ShortSword;
import com.mygdx.game.logic.GameLogicController;
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


public class CombatSample extends SampleBase {

    public final static SampleInfo SAMPLE_INFO = new SampleInfo(CombatSample.class);

    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private TextureRegistry textureRegistry;

    MapGenerator dungeonCreator = new DummyDungeonCreator();
    Map2D dungeon;
    ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    ItemRegistry itemRegistry = ItemRegistry.INSTANCE;
    GameLogicController gameLogicController = new GameLogicController(actorRegistry);
    OrthographicCamera infoCamera;
    Viewport infoViewPort;
    BitmapFont bitmapFont;
    Actor hero;

    @Override
    public void create() {

        infoCamera = new OrthographicCamera();
        infoViewPort = new FitViewport(1280, 720, infoCamera);
        bitmapFont = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(60, 60 * (h / w));
        spriteBatch = new SpriteBatch();
        spriteBatch.enableBlending();
        SpriteBatchRegistry.INSTANCE.setSpriteBatch(spriteBatch);

        dungeon = dungeonCreator.create();
        textureRegistry = TextureRegistry.INSTANCE;
        Gdx.input.setInputProcessor(this);

        hero = ActorFactory.INSTANCE.create(Warrior.class, dungeon, Placement.FIXED.X(1).Y(1));
        //Actor hero2 = ActorFactory.INSTANCE.create(Priest.class, dungeon, Placement.RANDOM);
        hero.getInventory().add(new SmallHealingPotion());
        hero.getInventory().add(new SmallHealingPotion());
        hero.getInventory().add(new SmallHealingPotion());
        hero.setLeftHandItem(new SmallShiled());

        Actor s = ActorFactory.INSTANCE.create(Skeleton.class, dungeon, Placement.FIXED.X(5).Y(0));
        //Actor s2 = ActorFactory.INSTANCE.create(Skeleton.class, dungeon, Placement.FIXED.X(0).Y(5));
        s.setRightHandItem(new ShortSword());
        //s2.setRightHandItem(new ShortSword());

        MapRegistry.INSTANCE.setCurrentMapToShow(dungeon);
        MapRegistry.INSTANCE.add(dungeon);

        hero.setName("Adavark");
        hero.setRightHandItem(new FlameTongue());
        CameraPositionController.INSTANCE.focusOn(hero);

    }

    @Override
    public void render() {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        GdxUtils.clearScreen();
        CameraPositionController.INSTANCE.updateCamera(camera);
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
//        System.out.println(Gdx.graphics.getFramesPerSecond());
        RendererBatch.DUNGEON.draw(dungeon, spriteBatch);

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
