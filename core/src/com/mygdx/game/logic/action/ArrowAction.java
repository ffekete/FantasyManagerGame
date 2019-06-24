package com.mygdx.game.logic.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.SpriteBatchRegistry;

public class ArrowAction implements Action {

    private final Texture arrowTexture = new Texture(Gdx.files.internal("effects/Arrow.png"));

    private Point coordinates;
    private Point targetCoordinates;

    public ArrowAction(Point coordinates, Point targetCoordinates, Map2D map) {
        this.coordinates = coordinates;
        this.targetCoordinates = targetCoordinates;

    }

    @Override
    public void update() {
        SpriteBatchRegistry.INSTANCE.getSpriteBatch().draw(arrowTexture, coordinates.getX(), coordinates.getY(), 0.0f, 0.0f, 1, 1, 1, 1, 0.0f, 0 ,0, 32, 32, false, false);
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
