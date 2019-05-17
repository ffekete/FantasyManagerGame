package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.common.SampleBase;
import com.mygdx.game.common.SampleInfo;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.creator.map.dungeon.Tile;
import com.mygdx.game.creator.map.dungeon.CaveDungeonCreator;
import com.mygdx.game.creator.map.dungeon.MapGenerator;
import com.mygdx.game.utils.GdxUtils;


public class DungeonWithRoomsRendererSample extends SampleBase {

    public final static SampleInfo SAMPLE_INFO = new SampleInfo(DungeonWithRoomsRendererSample.class);

    private OrthographicCamera camera;
    private Viewport viewPort;
    private SpriteBatch spriteBatch;
    private Texture wallTexture;
    private Texture floorTexture;
    private Texture playerTexture;

    MapGenerator dungeonCreator = new CaveDungeonCreator();
    Map2D dungeon;

    int px = 1, py = 1;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewPort = new FitViewport(Config.Dungeon.DUNGEON_WIDTH, Config.Dungeon.DUNGEON_HEIGHT, camera);
        spriteBatch = new SpriteBatch();
        wallTexture = new Texture(Gdx.files.internal("wall.jpg"));
        floorTexture = new Texture(Gdx.files.internal("terrain.jpg"));
        playerTexture = new Texture(Gdx.files.internal("badlogic.jpg"));
        dungeon = dungeonCreator.create();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {

        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();

        GdxUtils.clearScreen();
        draw();

        spriteBatch.end();

    }

    public void draw() {
        //VisibilityCalculator visibilityCalculator = new VisibilityCalculator(dungeon.getWidth(), dungeon.getHeight());
        //VisibilityMask visibilityMask = visibilityCalculator.generateMask(dungeon, 100, Arrays.asList(new Point(px,py)));
        //Tile[][] drawMap = visibilityMask.mask(dungeon, dungeon.getVisitedareaMap());

        //drawMap[px][py] = 3;

        for(int i = 0; i < Config.Dungeon.DUNGEON_WIDTH; i++) {
            for (int j = 0; j < Config.Dungeon.DUNGEON_HEIGHT; j++) {
                if(dungeon.getTile(i,j).equals(Tile.STONE_WALL)) {
                    spriteBatch.draw(wallTexture, i,j, 0,0,1,1,1,1,0,0,0, wallTexture.getWidth(), wallTexture.getHeight(), false, false);
                } else if(dungeon.getTile(i, j).equals(Tile.FLOOR)) {
                    spriteBatch.draw(floorTexture, i,j, 0,0,1,1,1,1,0,0,0,floorTexture.getWidth(), floorTexture.getHeight(), false, false);
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
        wallTexture.dispose();
        floorTexture.dispose();
        playerTexture.dispose();
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
            if(px < 0)
                px = 0;
        }
        if (keycode == Input.Keys.RIGHT) {
            //camera.position.x += 10.0 * delta;
            px++;
            if(px >= Config.Dungeon.DUNGEON_WIDTH)
                px = Config.Dungeon.DUNGEON_WIDTH -1;
        }
        if(keycode == Input.Keys.DOWN) {
            py--;
            if(py < 0)
                py = 0;
        }
        if(keycode == Input.Keys.UP) {
            py++;
            if(py >= Config.Dungeon.DUNGEON_HEIGHT)
                py = Config.Dungeon.DUNGEON_HEIGHT -1;
        }

        camera.update();

        return true;
    }
}
