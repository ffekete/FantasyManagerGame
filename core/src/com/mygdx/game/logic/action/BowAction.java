package com.mygdx.game.logic.action;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.logic.Point;
import com.mygdx.game.registry.RendererToolsRegistry;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.renderer.direction.DirectionSelector;

public class BowAction implements Action {

    private final TextureRegistry textureRegistry = TextureRegistry.INSTANCE;

    private Point coordinates;
    private float phase = 0f;
    private Direction direction;

    public BowAction(Actor actor) {
        this.coordinates = actor.getCoordinates();
        this.direction = DirectionSelector.INSTANCE.getDirection(actor);
    }

    @Override
    public void update() {
        RendererToolsRegistry.INSTANCE.getSpriteBatch().draw(textureRegistry.getActionTexture(BowAction.class), coordinates.getX(), coordinates.getY(), 0.0f, 0.0f, 1, 1, 1, 1, 0.0f, (int)phase * 32 ,0, 32, 32, direction.equals(Direction.UP) || direction.equals(Direction.LEFT), false);
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
