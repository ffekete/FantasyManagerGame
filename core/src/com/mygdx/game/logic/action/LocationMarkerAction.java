package com.mygdx.game.logic.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.Targetable;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.registry.RendererToolsRegistry;
import com.mygdx.game.registry.TextureRegistry;

public class LocationMarkerAction implements Action {

    private TextureRegistry textureRegistry = TextureRegistry.INSTANCE;

    private Point coordinates;
    private float phase = 0f;

    public LocationMarkerAction(Point target) {
        this.coordinates = target;
    }

    @Override
    public void update() {
        if(phase > textureRegistry.getActionTexture(LocationMarkerAction.class).getWidth() / 32)
            phase = 0f;

        RendererToolsRegistry.INSTANCE.getSpriteBatch().setColor(Color.valueOf("FFFFFF55"));
        RendererToolsRegistry.INSTANCE.getSpriteBatch().draw(textureRegistry.getActionTexture(LocationMarkerAction.class), coordinates.getX(), coordinates.getY(), 0.0f, 0.0f, 1, 1, 1f, 1f, 0.0f, (int)phase * 32 ,0, 32, 32, false, false);
        RendererToolsRegistry.INSTANCE.getSpriteBatch().setColor(Color.valueOf("FFFFFFFF"));
        phase += Gdx.graphics.getRawDeltaTime() * 5f;
    }

    @Override
    public void finish() {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void setCoordinates(Point newCoordinates) {
        this.coordinates = newCoordinates;
    }
}
