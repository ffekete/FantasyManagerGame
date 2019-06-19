package com.mygdx.game.logic.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.registry.SpriteBatchRegistry;
import com.mygdx.game.renderer.direction.DirectionSelector;

public class BloodSpillAction implements Action {

    private final int x;
    private final int y;
    private final Texture texture;
    private boolean finished = false;

    private float phase = 0f;

    public BloodSpillAction(int x, int y) {
        this.x = x;
        this.y = y;
        this.texture = new Texture(Gdx.files.internal("effects/BloodEffect.png"));
    }

    @Override
    public void update() {

        SpriteBatchRegistry.INSTANCE.getSpriteBatch().draw(texture, x, y, 0.0f, 0.0f, 1, 1, 1, 1, 0.0f, (int)phase * 32 ,0, (int)phase * 32 + 32, texture.getHeight(), false, false);
        phase += Gdx.graphics.getRawDeltaTime() * 10;
        if(phase == 3) {
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

}
