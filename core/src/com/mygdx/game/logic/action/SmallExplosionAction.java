package com.mygdx.game.logic.action;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.logic.Point;
import com.mygdx.game.registry.RendererToolsRegistry;
import com.mygdx.game.registry.TextureRegistry;

public class SmallExplosionAction implements Action {

    private final TextureRegistry textureRegistry = TextureRegistry.INSTANCE;

    private final int x;
    private final int y;
    private boolean finished = false;

    private float phase = 0f;

    public SmallExplosionAction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void update() {

        RendererToolsRegistry.INSTANCE.getSpriteBatch().draw(textureRegistry.getActionTexture(this.getClass()), x, y, 0.0f, 0.0f, 1, 1, 1, 1, 0.0f, ((int)phase) * 32 ,0, 32, 32, false, false);
        phase += Gdx.graphics.getRawDeltaTime() * 10;
        if(phase >= 3) {
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
