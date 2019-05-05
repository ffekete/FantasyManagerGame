package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.animation.ActorAnimation;
import com.mygdx.game.animation.BodyPartsBasedActorAnimation;
import com.mygdx.game.animation.FullBodyActorAnimation;
import com.mygdx.game.common.SampleBase;
import com.mygdx.game.common.SampleInfo;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.utils.GdxUtils;


public class AnimationSample extends SampleBase {

    public final static SampleInfo SAMPLE_INFO = new SampleInfo(AnimationSample.class);

    OrthographicCamera camera;
    Viewport viewPort;
    SpriteBatch spriteBatch;

    ActorAnimation actorAnimation;


    Actor a = new Warrior();

    @Override
    public void create() {

        camera = new OrthographicCamera(6, 6);
        viewPort = new FitViewport(6, 6, camera);
        spriteBatch = new SpriteBatch();

        actorAnimation = BodyPartsBasedActorAnimation.builder()
                .withHead(new Texture(Gdx.files.internal("black_plate_head.png")))
                .withTorso(new Texture(Gdx.files.internal("black_plate_torso.png")))
                .withLegs(new Texture(Gdx.files.internal("black_plate_leg.png")))
                .withArms(new Texture(Gdx.files.internal("black_plate_hand.png")))
                .build();

        actorAnimation = new FullBodyActorAnimation();
    }

    @Override
    public void render() {
        spriteBatch.setProjectionMatrix(camera.combined);
        viewPort.apply();
        spriteBatch.begin();
        GdxUtils.clearScreen(Color.WHITE);
        draw();
        spriteBatch.end();

    }

    public void draw() {
        actorAnimation.drawKeyFrame(spriteBatch, 3, 3,1, Direction.RIGHT, new MovementActivity(null, 1,1,1,null), Warrior.class);

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
