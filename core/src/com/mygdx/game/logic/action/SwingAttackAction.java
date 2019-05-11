package com.mygdx.game.logic.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.registry.SpriteBatchRegistry;

public class SwingAttackAction implements Action {

    private final int x;
    private final int y;
    private final Texture texture;
    private final Direction direction;

    private float angle;

    public SwingAttackAction(int x, int y, Texture texture, Direction direction) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.direction = direction;

    }

    @Override
    public void update() {
        float originX = 0.5f;
        float originY = 0.0f;

        float start = 0.0f;
        float end = 120f;
        float restart = 270;
        float inc = Gdx.graphics.getDeltaTime() * 15f;

        if(direction.equals(Direction.LEFT)) {

        }
        if(direction.equals(Direction.UP)) {

        }
        if(direction.equals(Direction.DOWN)) {
            start = 0;
            end = -120;
            restart = -270;
            inc = -1.f * inc;
        }
        if(direction.equals(Direction.RIGHT)) {
            start = 0;
            end = -120;
            restart = -270;
            inc = -1.f * inc;
        }

        angle += inc;
        if(inc > 0 && angle > restart) {
            angle = start;
        } else if(inc < 0 && angle < restart) {
            angle = start;
        }

        if((inc > 0 && angle < end) || (inc < 0 && angle > end))
            SpriteBatchRegistry.INSTANCE.getSpriteBatch().draw(texture, x,y + 0.5f, originX,originY, 1, 1, 1, 1, angle, 0,0, texture.getWidth(),texture.getHeight(), false, false);
    }

    @Override
    public void finish() {
    }

    @Override
    public boolean isFinished() {
        return false;
    }


}
