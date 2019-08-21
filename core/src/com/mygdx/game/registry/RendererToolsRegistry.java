package com.mygdx.game.registry;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;

public class RendererToolsRegistry {

    public static final RendererToolsRegistry INSTANCE = new RendererToolsRegistry();

    private SpriteBatch spriteBatch;
    private BitmapFont bitmapFont;
    private BitmapFont bitmapFontSmall;
    private BitmapFont bitmapFontSmallest;
    private BitmapFont bitmapFontTiny;
    private ShapeRenderer shapeRenderer;
    private Camera camera;
    private Camera infoCamera;
    private Viewport infoViewPort;

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

    public void setInfoViewPort(Viewport infoViewPort) {
        this.infoViewPort = infoViewPort;
    }

    public Viewport getInfoViewPort() {
        return infoViewPort;
    }

    public BitmapFont getBitmapFontSmall() {
        return bitmapFontSmall;
    }

    public void setBitmapFontSmall(BitmapFont bitmapFontSmall) {
        this.bitmapFontSmall = bitmapFontSmall;
    }

    public BitmapFont getBitmapFontSmallest() {
        return bitmapFontSmallest;
    }

    public BitmapFont getBitmapFontTiny() {
        return bitmapFontTiny;
    }

    public void setBitmapFontTiny(BitmapFont bitmapFontTiny) {
        this.bitmapFontTiny = bitmapFontTiny;
    }

    public void setBitmapFontSmallest(BitmapFont bitmapFontSmallest) {
        this.bitmapFontSmallest = bitmapFontSmallest;
    }
}
