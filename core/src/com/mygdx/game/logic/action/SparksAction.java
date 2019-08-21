package com.mygdx.game.logic.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.logic.Point;
import com.mygdx.game.registry.RendererToolsRegistry;
import com.mygdx.game.registry.TextureRegistry;

public class SparksAction implements Action {

    private final int x;
    private final int y;
    private Texture texture;
    private boolean finished = false;

    private float phase = 0f;

    public SparksAction(int x, int y) {
        this.x = x;
        this.y = y;
        texture = TextureRegistry.INSTANCE.getActionTexture(SparksAction.class);
    }

    @Override
    public void update() {

        RendererToolsRegistry.INSTANCE.getSpriteBatch().draw(texture, x, y, 0.0f, 0.0f, 1, 1, 1, 1, 0.0f, (int) phase * 32, 0, 32, 32, false, false);
        phase += Gdx.graphics.getRawDeltaTime() * 10;
        if (phase >= 3) {
            finished = true;
        }
    }

    @Override
    public void finish() {
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void setCoordinates(Point newCoordinates) {

    }

}
