package com.mygdx.game.logic.action;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.logic.Point;
import com.mygdx.game.registry.RendererToolsRegistry;
import com.mygdx.game.registry.TextureRegistry;

public class SlowAction implements Action {

    private TextureRegistry textureRegistry = TextureRegistry.INSTANCE;

    private Point coordinates;
    private float phase = 0f;

    public SlowAction() {

    }

    @Override
    public void update() {
        RendererToolsRegistry.INSTANCE.getSpriteBatch().draw(textureRegistry.getActionTexture(SlowAction.class), coordinates.getX(), coordinates.getY() -0.3f, 0.0f, 0.0f, 1, 1, 1, 1, 0.0f, (int)phase * 32 ,0, 32, 32, false, false);
        phase += Gdx.graphics.getRawDeltaTime() * 10;
    }

    @Override
    public void finish() {

    }

    @Override
    public boolean isFinished() {
        return phase >= 3;
    }

    @Override
    public void setCoordinates(Point newCoordinates) {
        this.coordinates = newCoordinates;
    }
}
