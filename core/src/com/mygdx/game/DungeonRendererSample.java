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
import com.mygdx.game.utils.GdxUtils;


public class DungeonRendererSample extends SampleBase {

    public final static SampleInfo SAMPLE_INFO = new SampleInfo(DungeonRendererSample.class);

    private OrthographicCamera camera;
    private Viewport viewPort;
    private SpriteBatch spriteBatch;
    private Texture texture;
    private Texture floorTexture;

    DungeonCreator dungeonCreator = new CaveDungeonCreator();
    Dungeon dungeon;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewPort = new FitViewport(Config.DungeonConfig.DUNGEON_WIDTH, Config.DungeonConfig.DUNGEON_HEIGHT, camera);
        spriteBatch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("wall.jpg"));
        floorTexture = new Texture(Gdx.files.internal("floor.jpg"));
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
        for(int i = 0; i < Config.DungeonConfig.DUNGEON_WIDTH; i++) {
            for (int j = 0; j < Config.DungeonConfig.DUNGEON_HEIGHT; j++) {
                if(dungeon.getTile(i, j) == 1) {
                    spriteBatch.draw(texture, i,j, 0,0,1,1,1,1,0,0,0,texture.getWidth(), texture.getHeight(), false, false);
                } else if(dungeon.getTile(i, j) == 2) {
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
        texture.dispose();
        floorTexture.dispose();
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
            camera.position.x -= 10.0 * delta;
        }
        if (keycode == Input.Keys.RIGHT) {
            camera.position.x += 10.0 * delta;
        }

        camera.update();

        return true;
    }
}
