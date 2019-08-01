package com.mygdx.game.logic.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.logic.Point;
import com.mygdx.game.registry.RendererToolsRegistry;
import com.mygdx.game.renderer.direction.DirectionSelector;

public class SwingAttackAction implements Action {

    private final DirectionSelector directionSelector = DirectionSelector.INSTANCE;

    private final int x;
    private final int y;
    private final Texture texture;
    private final Actor actor;
    private boolean finished = false;

    private float angle;

    public SwingAttackAction(int x, int y, Texture texture, Actor actor) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.actor = actor;

    }

    @Override
    public void update() {
        float originX = 0.5f;
        float originY = 0.0f;

        float start = 0.0f;
        float end = 120f;
        float restart = 270;
        float inc = 500f * Gdx.graphics.getRawDeltaTime();

        Direction direction = directionSelector.getDirection(actor);

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
            RendererToolsRegistry.INSTANCE.getSpriteBatch().draw(texture, x,y + (1f - Config.Engine.ACTOR_HEIGHT) / 2f, originX,originY, 1, 1, Config.Engine.ACTOR_HEIGHT, Config.Engine.ACTOR_HEIGHT, angle, 0,0, texture.getWidth(),texture.getHeight(), direction == Direction.UP || direction == Direction.LEFT, false);
        else
            finished = true;
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
