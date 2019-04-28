package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.monster.Goblin;
import com.mygdx.game.common.SampleBase;
import com.mygdx.game.common.SampleInfo;
import com.mygdx.game.creator.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.creator.map.dungeon.DungeonCreator;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.food.Bread;
import com.mygdx.game.item.weapon.ShortSword;
import com.mygdx.game.logic.GameLogicController;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.time.DayTimeCalculator;
import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.AnimationRegistry;
import com.mygdx.game.registry.ItemRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;
import com.mygdx.game.renderer.ActorRenderer;
import com.mygdx.game.renderer.ItemRenderer;
import com.mygdx.game.renderer.MapRenderer;
import com.mygdx.game.renderer.Renderer;
import com.mygdx.game.renderer.RendererBatch;
import com.mygdx.game.utils.GdxUtils;

import java.util.HashMap;
import java.util.Map;


public class DungeonRendererSample extends SampleBase {

    public final static SampleInfo SAMPLE_INFO = new SampleInfo(DungeonRendererSample.class);

    private OrthographicCamera camera;
    private Viewport viewPort;
    private SpriteBatch spriteBatch;
    private Texture grassVisitedTexture;
    private Texture playerTexture;
    private Texture breadTexture;
    private Texture actorTexture;
    private TextureRegistry textureRegistry;

    DungeonCreator dungeonCreator = new DummyDungeonCreator();
    com.mygdx.game.creator.map.dungeon.Dungeon dungeon;
    ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    ItemRegistry itemRegistry = ItemRegistry.INSTANCE;
    GameLogicController gameLogicController = new GameLogicController(actorRegistry);
    Map<Item, Texture> itemTextures = new HashMap<>();

    OrthographicCamera infoCamera;
    Viewport infoViewPort;
    BitmapFont bitmapFont;

    @Override
    public void create() {

        infoCamera = new OrthographicCamera();
        infoViewPort = new FitViewport(1280,720, infoCamera);
        bitmapFont = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
        //bitmapFont.getData().setScale(f);

        camera = new OrthographicCamera();
        viewPort = new FitViewport(100, 100, camera);
        spriteBatch = new SpriteBatch();

        grassVisitedTexture = new Texture(Gdx.files.internal("grass_visited.jpg"));
        playerTexture = new Texture(Gdx.files.internal("badlogic.jpg"));
        breadTexture = new Texture(Gdx.files.internal("bread.png"));

        dungeon = dungeonCreator.create();
        textureRegistry = TextureRegistry.INSTANCE;
        Gdx.input.setInputProcessor(this);

        Warrior warrior = new Warrior();
        warrior.setName("A");
        warrior.setCoordinates(85, 55);

        Warrior warrior2 = new Warrior();
        warrior2.setName("B");
        warrior2.setCoordinates(5, 5);

        warrior.setCurrentMap(dungeon);
        warrior2.setCurrentMap(dungeon);

        Bread bread = new Bread();
        bread.setCoordinates(10, 10);

        Bread bread2 = new Bread();
        bread2.setCoordinates(80, 80);

        itemRegistry.add(dungeon, bread);
        itemRegistry.add(dungeon, bread2);
        itemTextures.put(bread, breadTexture);

        Goblin goblin = new Goblin();
        goblin.setCurrentMap(dungeon);
        goblin.setCoordinates(50, 50);
        goblin.setName("Gobelin");

        actorRegistry.add(dungeon, warrior);
        actorRegistry.add(dungeon, warrior2);
        actorRegistry.add(dungeon, goblin);

        ShortSword shortSword = new ShortSword();
        shortSword.setCoordinates(88, 58);
        itemRegistry.add(dungeon, shortSword);

        MapRegistry.INSTANCE.add(dungeon);

    }

    @Override
    public void render() {
        spriteBatch.setProjectionMatrix(camera.combined);
        viewPort.apply();
        spriteBatch.begin();

        GdxUtils.clearScreen();
        gameLogicController.update();
        draw();

        spriteBatch.end();

        infoViewPort.apply();
        spriteBatch.setProjectionMatrix(infoCamera.combined);
        spriteBatch.begin();
        bitmapFont.draw(spriteBatch, "Hour: " + DayTimeCalculator.INSTANCE.getHour() + " Day: " + DayTimeCalculator.INSTANCE.getDay(), 10,40);
        spriteBatch.end();
    }

    public void draw() {
        RendererBatch.DUNGEON.draw(dungeon, spriteBatch);
    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height, true);
        infoViewPort.update(width, height, true);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        playerTexture.dispose();
        grassVisitedTexture.dispose();
        textureRegistry.dispose();
        bitmapFont.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //dungeon = dungeonCreator.create();
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        float delta = Gdx.graphics.getDeltaTime();
        camera.zoom += amount * delta;
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

        camera.update();

        return true;
    }
}
