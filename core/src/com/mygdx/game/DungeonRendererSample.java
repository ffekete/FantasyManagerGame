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
import com.mygdx.game.actor.monster.Orc;
import com.mygdx.game.actor.monster.Skeleton;
import com.mygdx.game.common.SampleBase;
import com.mygdx.game.common.SampleInfo;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.creator.map.dungeon.cave.CaveDungeonCreator;
import com.mygdx.game.creator.map.dungeon.MapGenerator;
import com.mygdx.game.item.food.Bread;
import com.mygdx.game.item.potion.SmallHealingPotion;
import com.mygdx.game.item.shield.Shield;
import com.mygdx.game.item.shield.SmallShiled;
import com.mygdx.game.item.weapon.sword.FlameTongue;
import com.mygdx.game.item.weapon.sword.ShortSword;
import com.mygdx.game.logic.GameLogicController;
import com.mygdx.game.logic.time.DayTimeCalculator;
import com.mygdx.game.object.decoration.SpiderWeb;
import com.mygdx.game.object.decoration.StandingTorch;
import com.mygdx.game.object.factory.WorldObjectFactory;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.registry.*;
import com.mygdx.game.renderer.RendererBatch;
import com.mygdx.game.renderer.camera.CameraPositionController;
import com.mygdx.game.renderer.gui.ActorGuiRenderer;
import com.mygdx.game.utils.GdxUtils;


public class DungeonRendererSample extends SampleBase {

    public final static SampleInfo SAMPLE_INFO = new SampleInfo(DungeonRendererSample.class);

    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private TextureRegistry textureRegistry;

    MapGenerator dungeonCreator = new CaveDungeonCreator();
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
        camera = new OrthographicCamera(60, 60 * (w/h));

        spriteBatch = new SpriteBatch(150);
        spriteBatch.enableBlending();
        SpriteBatchRegistry.INSTANCE.setSpriteBatch(spriteBatch);

        dungeon = dungeonCreator.create(3);
        textureRegistry = TextureRegistry.INSTANCE;
        Gdx.input.setInputProcessor(this);

        hero = ActorFactory.INSTANCE.create(Warrior.class, dungeon, Placement.RANDOM);
        //Actor hero2 = ActorFactory.INSTANCE.create(Priest.class, dungeon, Placement.RANDOM);
        hero.getInventory().add(new SmallHealingPotion());
        hero.getInventory().add(new SmallHealingPotion());
        hero.getInventory().add(new SmallHealingPotion());
        hero.setLeftHandItem(new SmallShiled());

        for (int i = 0; i < 5; i++) {
            Actor s = ActorFactory.INSTANCE.create(Skeleton.class, dungeon, Placement.RANDOM);
            s.setRightHandItem(new ShortSword());
        }

        for (int i = 0; i < 5; i++) {
            Actor s = ActorFactory.INSTANCE.create(Goblin.class, dungeon, Placement.RANDOM);
            s.setRightHandItem(new ShortSword());
        }

        for (int i = 0; i < 5; i++) {
            Actor s = ActorFactory.INSTANCE.create(Orc.class, dungeon, Placement.RANDOM);
            s.setRightHandItem(new ShortSword());
            s.setLeftHandItem(new SmallShiled());

        }

        MapRegistry.INSTANCE.setCurrentMapToShow(dungeon);

        Bread bread = new Bread();
        bread.setCoordinates(10, 10);

        Bread bread2 = new Bread();
        bread2.setCoordinates(80, 80);

        Shield shield = new SmallShiled();
        shield.setCoordinates(89, 59);

        ShortSword shortSword = new ShortSword();
        shortSword.setCoordinates(88, 58);
        //itemRegistry.add(dungeon, shortSword);
        //itemRegistry.add(dungeon, shield);
        //itemRegistry.add(dungeon, bread);
        //itemRegistry.add(dungeon, bread2);

        MapRegistry.INSTANCE.add(dungeon);

        hero.setName("Adavark");
        hero.setRightHandItem(new FlameTongue());
        CameraPositionController.INSTANCE.focusOn(hero);

        StandingTorch standingTorch = WorldObjectFactory.create(StandingTorch.class, dungeon, ObjectPlacement.FIXED.X(hero.getX()).Y(hero.getY()));
    }

    @Override
    public void render() {
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();
        GdxUtils.clearScreen();
        CameraPositionController.INSTANCE.updateCamera(camera);
        gameLogicController.update();
        draw();

        infoViewPort.apply();
        spriteBatch.setProjectionMatrix(infoCamera.combined);

        ActorGuiRenderer.INSTANCE.render(spriteBatch, hero);
        bitmapFont.draw(spriteBatch, "Hour: " + DayTimeCalculator.INSTANCE.getHour() + " Day: " + DayTimeCalculator.INSTANCE.getDay(), 400, 40);
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
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(60, 60 * (h / w));
        camera.update();
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

        if(keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
            System.exit(0);
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
