package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.common.SampleBase;
import com.mygdx.game.common.SampleInfo;
import com.mygdx.game.utils.GdxUtils;


public class TextSample extends SampleBase {

    public final static SampleInfo SAMPLE_INFO = new SampleInfo(TextSample.class);


    OrthographicCamera camera;
    Viewport viewPort;
    SpriteBatch spriteBatch;

    OrthographicCamera infoCamera;
    Viewport infoViewPort;
    BitmapFont bitmapFont;

    @Override
    public void create() {


        infoCamera = new OrthographicCamera();
        infoViewPort = new FitViewport(100,100, infoCamera);
        bitmapFont = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));

        camera = new OrthographicCamera(1024, 768);
        viewPort = new FitViewport(1280, 720, camera);
        spriteBatch = new SpriteBatch();

        infoCamera.update();

    }

    @Override
    public void render() {
        spriteBatch.setProjectionMatrix(camera.combined);
        viewPort.apply();
        spriteBatch.begin();
        GdxUtils.clearScreen();
        bitmapFont.draw(spriteBatch, "ASDFGGHJHF", 10,10);
        spriteBatch.end();

    }

    public void draw() {



    }



    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height, true);
        infoViewPort.update(100, 100);

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        bitmapFont.dispose();
    }


    @Override
    public boolean scrolled(int amount) {
        float delta = Gdx.graphics.getDeltaTime();
        camera.zoom += amount * delta;
        camera.update();
        return true;
    }
}
