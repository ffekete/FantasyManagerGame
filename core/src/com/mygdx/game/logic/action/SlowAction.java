package com.mygdx.game.logic.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.SpriteBatchRegistry;

public class SlowAction implements Action {

    private final Texture texture = new Texture(Gdx.files.internal("effects/Slow.png"));

    private Point coordinates;
    private float phase = 0f;

    public SlowAction(Point coordinates) {
        this.coordinates = coordinates;

    }

    @Override
    public void update() {
        SpriteBatchRegistry.INSTANCE.getSpriteBatch().draw(texture, coordinates.getX(), coordinates.getY() -0.3f, 0.0f, 0.0f, 1, 1, 1, 1, 0.0f, (int)phase * 32 ,0, 32, 32, false, false);
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
