package com.mygdx.game.logic.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.logic.Point;
import com.mygdx.game.registry.SpriteBatchRegistry;

public class SmallExplosionAction implements Action {

    private final int x;
    private final int y;
    private final Texture texture = new Texture(Gdx.files.internal("effects/ExplosionEffect.png"));
    private boolean finished = false;

    private float phase = 0f;

    public SmallExplosionAction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void update() {

        SpriteBatchRegistry.INSTANCE.getSpriteBatch().draw(texture, x, y, 0.0f, 0.0f, 1, 1, 1, 1, 0.0f, ((int)phase) * 32 ,0, 32, 32, false, false);
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
