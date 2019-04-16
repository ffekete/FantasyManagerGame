package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.common.SampleBase;
import com.mygdx.game.common.SampleInfo;
import com.mygdx.game.creator.map.MapGenerator;
import com.mygdx.game.creator.map.RandomMapGenerator;

import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.Text;

public class MyGdxGame extends SampleBase {


    SpriteBatch batch;

    Viewport viewport;
    OrthographicCamera camera;

    public final static SampleInfo SAMPLE_INFO = new SampleInfo(MyGdxGame.class);

    private static final MapGenerator mapGenerator = new RandomMapGenerator();

    List<Texture> textures;
    Integer[][] map;
    Texture t1;
    Texture t2;

    @Override
    public void create() {

        camera = new OrthographicCamera();
        viewport = new FitViewport(10, 10, camera);

        batch = new SpriteBatch();
        textures = new ArrayList<Texture>();

        t1 = new Texture("grass.jpg");
        t2 = new Texture("terrain.jpg");

        textures.add(t1);
        textures.add(t2);

        map = mapGenerator.generate(textures.size());

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        //Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        BitmapFont bitmapFont = new BitmapFont();
        batch.begin();
        for (int i = (int)camera.position.x; i < (int)camera.position.x + 20; i++)
            for (int j = (int)camera.position.y; j < (int)camera.position.y + 20; j++) {
                batch.draw(textures.get(map[i][j]), i-10, j-10, 0.5f, 0.5f, 1, 1, 1.0f, 1.0f, 0.0f, 1, 1, textures.get(map[i][j]).getWidth(), textures.get(map[i][j]).getHeight(), false, false);
            }
        bitmapFont.draw(batch, Integer.valueOf(Gdx.graphics.getFramesPerSecond()).toString(), 1, 1);
        batch.end();


    }

    @Override
    public void dispose() {
        batch.dispose();
        t1.dispose();
        t2.dispose();
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
