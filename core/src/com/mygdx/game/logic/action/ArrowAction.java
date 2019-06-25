package com.mygdx.game.logic.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.SpriteBatchRegistry;
import com.mygdx.game.renderer.direction.DirectionSelector;

public class ArrowAction implements Action {

    private final Texture arrowTexture = new Texture(Gdx.files.internal("effects/Arrow.png"));

    private Point coordinates;
    private Point targetCoordinates;
    private Direction direction;

    public ArrowAction(Actor actor, Point targetCoordinates) {
        this.coordinates = actor.getCoordinates();
        this.targetCoordinates = targetCoordinates;
        direction = DirectionSelector.INSTANCE.getDirection(actor);

    }

    @Override
    public void update() {
        SpriteBatchRegistry.INSTANCE.getSpriteBatch().draw(arrowTexture, coordinates.getX(), coordinates.getY(), getOriginXBasedOnDirection(), getOriginXBasedOnDirection(), 1, 1, 1, 1, getRotationBasedOnDirection(), 0 ,0, 32, 32, direction.equals(Direction.LEFT), false);
    }

    private float getRotationBasedOnDirection() {
        if (direction.equals(Direction.UP))
         return 90.f;

        if (direction.equals(Direction.DOWN))
            return -90.f;

        return  0.0f;
    }

    private float getOriginXBasedOnDirection() {
        return direction.equals(Direction.UP) || direction.equals(Direction.DOWN) ? 0.5f : 0.0f;
    }

    @Override
    public void finish() {

    }

    @Override
    public boolean isFinished() {
        return targetCoordinates.equals(coordinates);
    }

    @Override
    public void setCoordinates(Point newCoordinates) {
        this.coordinates = newCoordinates;
    }
}
