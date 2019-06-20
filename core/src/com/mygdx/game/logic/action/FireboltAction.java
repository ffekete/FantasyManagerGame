package com.mygdx.game.logic.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.ActionRegistry;
import com.mygdx.game.registry.SpriteBatchRegistry;

public class FireboltAction implements Action {

    private final Texture fireboltTexture = new Texture(Gdx.files.internal("effects/FireBolt.png"));

    private Point coordinates;
    private Point targetCoordinates;
    private float phase = 0f;
    private Map2D map;

    public FireboltAction(Point coordinates, Point targetCoordinates, Map2D map) {
        this.coordinates = coordinates;
        this.targetCoordinates = targetCoordinates;
        this.map = map;
    }

    @Override
    public void update() {
        SpriteBatchRegistry.INSTANCE.getSpriteBatch().draw(fireboltTexture, coordinates.getX(), coordinates.getY(), 0.0f, 0.0f, 1, 1, 1, 1, 0.0f, (int)phase * 32 ,0, (int)phase * 32 + 32, fireboltTexture.getHeight(), false, false);
        phase += Gdx.graphics.getRawDeltaTime() * 10;
        if(phase >= 3f) {
            phase = 0f;
        }
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
