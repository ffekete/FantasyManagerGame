package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.monster.Goblin;
import com.mygdx.game.common.SampleBase;
import com.mygdx.game.common.SampleInfo;
import com.mygdx.game.creator.map.Tile;
import com.mygdx.game.creator.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.creator.map.dungeon.DungeonCreator;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.food.Bread;
import com.mygdx.game.item.weapon.ShortSword;
import com.mygdx.game.logic.GameLogicController;
import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.logic.visibility.VisitedArea;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.ItemRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;
import com.mygdx.game.utils.GdxUtils;

import java.util.HashMap;
import java.util.Map;


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
