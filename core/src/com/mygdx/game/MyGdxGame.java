package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.common.SampleBase;

public class MyGdxGame extends SampleBase {


    SpriteBatch batch;

    Viewport viewport;
    OrthographicCamera camera;


    @Override
    public void create() {

        camera = new OrthographicCamera();
        viewport = new FitViewport(10, 10, camera);

        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {

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
            camera.position.x -= 10.0 * delta;
        }
        if (keycode == Input.Keys.RIGHT) {
            camera.position.x += 10.0 * delta;
        }

        camera.update();

        return true;
    }
}
