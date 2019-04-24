package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.monster.Goblin;
import com.mygdx.game.common.SampleBase;
import com.mygdx.game.common.SampleInfo;
import com.mygdx.game.creator.map.Tile;
import com.mygdx.game.creator.map.dungeon.CaveDungeonCreator;
import com.mygdx.game.creator.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.creator.map.dungeon.DungeonCreator;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.item.Bread;
import com.mygdx.game.item.Item;
import com.mygdx.game.logic.GameLogicController;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.logic.visibility.VisibilityCalculator;
import com.mygdx.game.logic.visibility.VisitedArea;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.ItemRegistry;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.utils.GdxUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private Texture breadTexture;
    private Texture actorTexture;
    private TextureRegistry textureRegistry;

    DungeonCreator dungeonCreator = new DummyDungeonCreator();
    com.mygdx.game.creator.map.dungeon.Dungeon dungeon;
    ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    ItemRegistry itemRegistry = ItemRegistry.INSTANCE;
    GameLogicController gameLogicController = new GameLogicController(actorRegistry);
    PathFinder pathFinder;
    Map<Item, Texture> itemTextures = new HashMap<>();
    VisibilityCalculator visibilityCalculator;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewPort = new FitViewport(Config.Dungeon.DUNGEON_WIDTH, Config.Dungeon.DUNGEON_HEIGHT, camera);
        spriteBatch = new SpriteBatch();
        wallTexture = new Texture(Gdx.files.internal("wall.jpg"));
        floorTexture = new Texture(Gdx.files.internal("terrain.jpg"));
        grassVisitedTexture = new Texture(Gdx.files.internal("grass_visited.jpg"));
        playerTexture = new Texture(Gdx.files.internal("badlogic.jpg"));
        breadTexture = new Texture(Gdx.files.internal("bread.png"));
        actorTexture = new Texture(Gdx.files.internal("warrior.png"));
        dungeon = dungeonCreator.create();
        pathFinder = new PathFinder(dungeon.getWidth(), dungeon.getHeight());
        textureRegistry = TextureRegistry.INSTANCE;
        Gdx.input.setInputProcessor(this);

        Warrior warrior = new Warrior();

        int x = 0;
        int y = 0;

        do {
            x = new Random().nextInt(dungeon.getWidth());
            y = new Random().nextInt(dungeon.getHeight());
        } while(dungeon.getTile(x,y).isObstacle());
        warrior.setCoordinates(55, 55);

        Warrior warrior2 = new Warrior();

        do {
            x = new Random().nextInt(dungeon.getWidth());
            y = new Random().nextInt(dungeon.getHeight());
        } while(dungeon.getTile(x,y).isObstacle());
        warrior2.setCoordinates(45, 45);

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

        actorRegistry.add(warrior);
        actorRegistry.add(warrior2);
        actorRegistry.add(goblin);

        visibilityCalculator = new VisibilityCalculator(dungeon.getWidth(), dungeon.getHeight());

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
    }

    public void draw() {

        List<Point> coordinatesForVisibilityCalculation = new ArrayList<>();
        actorRegistry.getActors().forEach(actor -> {
                    if(Alignment.FRIENDLY.equals(actor.getAlignment()))
                        coordinatesForVisibilityCalculation.add(new Point(actor.getX(), actor.getY()));
                }
        );

        VisibilityMask visibilityMask = visibilityCalculator.generateMask(dungeon, 15, coordinatesForVisibilityCalculation);
        Tile[][] drawMap = visibilityMask.mask(dungeon, dungeon.getVisitedareaMap());

        for (int i = 0; i < Config.Dungeon.DUNGEON_WIDTH; i++) {
            for (int j = 0; j < Config.Dungeon.DUNGEON_HEIGHT; j++) {

                if(dungeon.getVisitedareaMap()[i][j] == VisitedArea.VISITED_BUT_NOT_VISIBLE) {
                    spriteBatch.setColor(Color.GRAY);
                } else {
                    spriteBatch.setColor(0xff,0xff,0xff,0xff);
                }
                if (drawMap[i][j].equals(Tile.STONE_WALL)) {
                    spriteBatch.draw(wallTexture, i, j, 0, 0, 1, 1, 1, 1, 0, 0, 0, wallTexture.getWidth(), wallTexture.getHeight(), false, false);
                } else if (drawMap[i][j].equals(Tile.FLOOR)) {
                    spriteBatch.draw(floorTexture, i, j, 0, 0, 1, 1, 1, 1, 0, 0, 0, floorTexture.getWidth(), floorTexture.getHeight(), false, false);
                }
            }
        }

        for(Item item : itemRegistry.getAllItems(dungeon)) {
            if(visibilityMask.getValue(item.getX(), item.getY()) == 1)
                spriteBatch.draw(breadTexture, item.getX(), item.getY(), 0,0,1,1,1,1,0, 0,0,breadTexture.getWidth(), breadTexture.getHeight(), false, false);
        }
        for(Actor actor : actorRegistry.getActors()) {
            if(Alignment.FRIENDLY.equals(actor.getAlignment()) || visibilityMask.getValue(actor.getX(), actor.getY()) == 1)
                spriteBatch.draw(textureRegistry.getFor(actor.getClass()), actor.getX()-1 + actor.getxOffset(), actor.getY()-1 + actor.getyOffset(), 0,0,3,3,1,1,0, 0,0,actorTexture.getWidth(), actorTexture.getHeight(), false, false);
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
            if (px >= Config.Dungeon.DUNGEON_WIDTH)
                px = Config.Dungeon.DUNGEON_WIDTH - 1;
        }
        if (keycode == Input.Keys.DOWN) {
            py--;
            if (py < 0)
                py = 0;
        }
        if (keycode == Input.Keys.UP) {
            py++;
            if (py >= Config.Dungeon.DUNGEON_HEIGHT)
                py = Config.Dungeon.DUNGEON_HEIGHT - 1;
        }

        camera.update();

        return true;
    }
}
