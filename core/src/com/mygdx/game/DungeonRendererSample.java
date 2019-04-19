package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.actor.hero.Hero;
import com.mygdx.game.common.SampleBase;
import com.mygdx.game.common.SampleInfo;
import com.mygdx.game.creator.map.Tile;
import com.mygdx.game.creator.map.dungeon.CaveDungeonCreator;
import com.mygdx.game.creator.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.creator.map.dungeon.DungeonCreator;
import com.mygdx.game.creator.map.dungeon.Dungeon;
import com.mygdx.game.logic.GameLogicController;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.VisibilityMask;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.MovementActivity;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.logic.visibility.VisibilityCalculator;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.utils.GdxUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DungeonRendererSample extends SampleBase {

    public final static SampleInfo SAMPLE_INFO = new SampleInfo(DungeonRendererSample.class);

    private int px;
    private int py;

    private OrthographicCamera camera;
    private Viewport viewPort;
    private SpriteBatch spriteBatch;
    private Texture wallTexture;
    private Texture floorTexture;
    private Texture grassVisitedTexture;
    private Texture playerTexture;

    DungeonCreator dungeonCreator = new DummyDungeonCreator();
    Dungeon dungeon;
    ActorRegistry actorRegistry = new ActorRegistry();
    GameLogicController gameLogicController = new GameLogicController(actorRegistry);
    PathFinder pathFinder;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewPort = new FitViewport(Config.DungeonConfig.DUNGEON_WIDTH, Config.DungeonConfig.DUNGEON_HEIGHT, camera);
        spriteBatch = new SpriteBatch();
        wallTexture = new Texture(Gdx.files.internal("wall.jpg"));
        floorTexture = new Texture(Gdx.files.internal("terrain.jpg"));
        grassVisitedTexture = new Texture(Gdx.files.internal("grass_visited.jpg"));
        playerTexture = new Texture(Gdx.files.internal("badlogic.jpg"));
        dungeon = dungeonCreator.create();
        pathFinder = new PathFinder(dungeon.getWidth(), dungeon.getHeight());
        Gdx.input.setInputProcessor(this);



        Hero hero = new Hero();

        int x = 0;
        int y = 0;

        do {
            x = new Random().nextInt(dungeon.getWidth());
            y = new Random().nextInt(dungeon.getHeight());
        } while(dungeon.getTile(x,y).isObstacle());
        hero.setCoordinates(x, y);

        do {
            x = new Random().nextInt(dungeon.getWidth());
            y = new Random().nextInt(dungeon.getHeight());
        } while(dungeon.getTile(x,y).isObstacle());

        Activity activity = new MovementActivity(hero, x, y, pathFinder);

        do {
            x = new Random().nextInt(dungeon.getWidth());
            y = new Random().nextInt(dungeon.getHeight());
        } while(dungeon.getTile(x,y).isObstacle());

        Activity activity2 = new MovementActivity(hero, x, y, pathFinder);
        hero.getActivityStack().add(activity);
        hero.getActivityStack().add(activity2);

        Hero hero2 = new Hero();

        do {
            x = new Random().nextInt(dungeon.getWidth());
            y = new Random().nextInt(dungeon.getHeight());
        } while(dungeon.getTile(x,y).isObstacle());
        hero2.setCoordinates(x, y);

        do {
            x = new Random().nextInt(dungeon.getWidth());
            y = new Random().nextInt(dungeon.getHeight());
        } while(dungeon.getTile(x,y).isObstacle());

        Activity activity3 = new MovementActivity(hero2, x, y, pathFinder);

        do {
            x = new Random().nextInt(dungeon.getWidth());
            y = new Random().nextInt(dungeon.getHeight());
        } while(dungeon.getTile(x,y).isObstacle());

        Activity activity4 = new MovementActivity(hero2, x, y, pathFinder);
        hero2.getActivityStack().add(activity3);
        hero2.getActivityStack().add(activity4);

        hero.setCurrentMap(dungeon);
        hero2.setCurrentMap(dungeon);

        actorRegistry.add(hero);
        actorRegistry.add(hero2);
    }

    @Override
    public void render() {

        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();

        GdxUtils.clearScreen();
        gameLogicController.update();
        draw();

        spriteBatch.end();

    }

    public void draw() {
        VisibilityCalculator visibilityCalculator = new VisibilityCalculator(dungeon.getWidth(), dungeon.getHeight());

        List<Point> coordinatesForVisibilityCalculation = new ArrayList<>();
        actorRegistry.getActors().forEach(actor -> {
                    coordinatesForVisibilityCalculation.add(new Point(actor.getX(), actor.getY()));
                }
        );

        VisibilityMask visibilityMask = visibilityCalculator.generateMask(dungeon, 10, coordinatesForVisibilityCalculation);
        Tile[][] drawMap = visibilityMask.mask(dungeon);

        actorRegistry.getActors().forEach(actor -> {
            //drawMap[actor.getX()][actor.getY()] = 3;
                }
        );

        for (int i = 0; i < Config.DungeonConfig.DUNGEON_WIDTH; i++) {
            for (int j = 0; j < Config.DungeonConfig.DUNGEON_HEIGHT; j++) {
                if (drawMap[i][j].equals(Tile.STONE_WALL)) {
                    spriteBatch.draw(wallTexture, i, j, 0, 0, 1, 1, 1, 1, 0, 0, 0, wallTexture.getWidth(), wallTexture.getHeight(), false, false);
                } else if (drawMap[i][j].equals(Tile.FLOOR)) {
                    spriteBatch.draw(floorTexture, i, j, 0, 0, 1, 1, 1, 1, 0, 0, 0, floorTexture.getWidth(), floorTexture.getHeight(), false, false);
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height, true);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        playerTexture.dispose();
        wallTexture.dispose();
        floorTexture.dispose();
        grassVisitedTexture.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        dungeon = dungeonCreator.create();
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
            //camera.position.x -= 10.0 *
            px--;
            if (px < 0)
                px = 0;
        }
        if (keycode == Input.Keys.RIGHT) {
            //camera.position.x += 10.0 * delta;
            px++;
            if (px >= Config.DungeonConfig.DUNGEON_WIDTH)
                px = Config.DungeonConfig.DUNGEON_WIDTH - 1;
        }
        if (keycode == Input.Keys.DOWN) {
            py--;
            if (py < 0)
                py = 0;
        }
        if (keycode == Input.Keys.UP) {
            py++;
            if (py >= Config.DungeonConfig.DUNGEON_HEIGHT)
                py = Config.DungeonConfig.DUNGEON_HEIGHT - 1;
        }

        camera.update();

        return true;
    }
}
