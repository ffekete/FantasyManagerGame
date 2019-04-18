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
import com.mygdx.game.creator.dungeon.CaveDungeonCreator;
import com.mygdx.game.creator.dungeon.DungeonCreator;
import com.mygdx.game.dto.Dungeon;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.VisibilityMask;
import com.mygdx.game.logic.visibility.VisibilityCalculator;
import com.mygdx.game.utils.GdxUtils;


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

    DungeonCreator dungeonCreator = new CaveDungeonCreator();
    Dungeon dungeon;

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
        VisibilityCalculator visibilityCalculator = new VisibilityCalculator(dungeon.getWidth(), dungeon.getHeight());
        VisibilityMask visibilityMask = visibilityCalculator.generateMask(dungeon, 100,  new Point(px,py));
        int[][] drawMap = visibilityMask.mask(dungeon);

        drawMap[px][py] = 3;

        for(int i = 0; i < Config.DungeonConfig.DUNGEON_WIDTH; i++) {
            for (int j = 0; j < Config.DungeonConfig.DUNGEON_HEIGHT; j++) {
                if(drawMap[i][j] == 3) {
                    spriteBatch.draw(playerTexture, i,j, 0,0,1,1,1,1,0,0,0, playerTexture.getWidth(), playerTexture.getHeight(), false, false);
                }
                if(drawMap[i][j] == 4) {
                    spriteBatch.draw(grassVisitedTexture, i, j, 0, 0, 1, 1, 1, 1, 0, 0, 0, grassVisitedTexture.getWidth(), grassVisitedTexture.getHeight(), false, false);
                }
                if(drawMap[i][j] == 2) {
                    spriteBatch.draw(wallTexture, i,j, 0,0,1,1,1,1,0,0,0, wallTexture.getWidth(), wallTexture.getHeight(), false, false);
                } else if(drawMap[i][j] == 1) {
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

        System.out.println("Key pressed " + keycode);

        if (keycode == Input.Keys.LEFT) {
            //camera.position.x -= 10.0 *
            px--;
            if(px < 0)
                px = 0;
        }
        if (keycode == Input.Keys.RIGHT) {
            //camera.position.x += 10.0 * delta;
            px++;
            if(px >= Config.DungeonConfig.DUNGEON_WIDTH)
                px = Config.DungeonConfig.DUNGEON_WIDTH -1;
        }
        if(keycode == Input.Keys.DOWN) {
            py--;
            if(py < 0)
                py = 0;
        }
        if(keycode == Input.Keys.UP) {
            py++;
            if(py >= Config.DungeonConfig.DUNGEON_HEIGHT)
                py = Config.DungeonConfig.DUNGEON_HEIGHT -1;
        }

        camera.update();

        return true;
    }
}
