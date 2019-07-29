package com.mygdx.game.registry;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class RendererToolsRegistry {

    public static final RendererToolsRegistry INSTANCE = new RendererToolsRegistry();

    private SpriteBatch spriteBatch;
    private BitmapFont bitmapFont;
    private ShapeRenderer shapeRenderer;
    private Camera camera;
    private Camera infoCamera;


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

    private RendererToolsRegistry() {}
}
