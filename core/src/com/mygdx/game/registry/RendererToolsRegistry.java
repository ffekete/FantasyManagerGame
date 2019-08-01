package com.mygdx.game.registry;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.logic.GameState;

import java.util.HashMap;
import java.util.Map;

public class RendererToolsRegistry {

    public static final RendererToolsRegistry INSTANCE = new RendererToolsRegistry();

    private SpriteBatch spriteBatch;
    private BitmapFont bitmapFont;
    private ShapeRenderer shapeRenderer;
    private Camera camera;
    private Camera infoCamera;
    private Viewport infoViewPort;

    private Map<GameState, Stage> stages = new HashMap<>();


    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public BitmapFont getBitmapFont() {
        return bitmapFont;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public Camera getCamera() {
        return camera;
    }

    public Camera getInfoCamera() {
        return infoCamera;
    }

    public void setSpriteBatch(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    public void setBitmapFont(BitmapFont bitmapFont) {
        this.bitmapFont = bitmapFont;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setInfoCamera(Camera infoCamera) {
        this.infoCamera = infoCamera;
    }

    private RendererToolsRegistry() {
    }

    public void addStage(GameState gameState, Stage stage) {
        this.stages.put(gameState, stage);
    }

    public Stage getStage(GameState gameState) {
        return this.stages.get(gameState);
    }

    public void setInfoViewPort(Viewport infoViewPort) {
        this.infoViewPort = infoViewPort;
    }

    public Viewport getInfoViewPort() {
        return infoViewPort;
    }
}
