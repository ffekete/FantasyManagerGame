package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.animation.Animation;
import com.mygdx.game.common.SampleBase;
import com.mygdx.game.common.SampleInfo;
import com.mygdx.game.utils.GdxUtils;


public class AnimationSample extends SampleBase {

    public final static SampleInfo SAMPLE_INFO = new SampleInfo(AnimationSample.class);

    OrthographicCamera camera;
    Viewport viewPort;
    SpriteBatch spriteBatch;

    Animation animation;

    @Override
    public void create() {

        camera = new OrthographicCamera(6, 6);
        viewPort = new FitViewport(6, 6, camera);
        spriteBatch = new SpriteBatch();

        animation = Animation.builder()
                .withHead(new Texture(Gdx.files.internal("head_idle.png")))
                .withTorso(new Texture(Gdx.files.internal("torso_idle.png")))
                .withLegs(new Texture(Gdx.files.internal("leg_idle.png")))
                .withArms(new Texture(Gdx.files.internal("hand_idle.png")))
                .build();
    }

    @Override
    public void render() {
        spriteBatch.setProjectionMatrix(camera.combined);
        viewPort.apply();
        spriteBatch.begin();
        GdxUtils.clearScreen();
        draw();
        spriteBatch.end();

    }

    public void draw() {

        animation.drawKeyFrame(spriteBatch, 3, 3,2, false);

    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height, true);

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }


    @Override
    public boolean scrolled(int amount) {
        float delta = Gdx.graphics.getDeltaTime();
        camera.zoom += amount * delta;
        camera.update();
        return true;
    }
}
