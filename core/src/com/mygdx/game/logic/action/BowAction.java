package com.mygdx.game.logic.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.SpriteBatchRegistry;

public class BowAction implements Action {

    private final Texture bowTexture = new Texture(Gdx.files.internal("effects/BowEffect.png"));

    private Point coordinates;
    private float phase = 0f;
    private Map2D map;

    public BowAction(Point coordinates, Map2D map) {
        this.coordinates = coordinates;
        this.map = map;
    }

    @Override
    public void update() {
        SpriteBatchRegistry.INSTANCE.getSpriteBatch().draw(bowTexture, coordinates.getX(), coordinates.getY(), 0.0f, 0.0f, 1, 1, 1, 1, 0.0f, (int)phase * 32 ,0, 32, 32, false, false);
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
