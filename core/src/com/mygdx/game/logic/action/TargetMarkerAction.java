package com.mygdx.game.logic.action;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.Targetable;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.registry.RendererToolsRegistry;
import com.mygdx.game.registry.TextureRegistry;

public class TargetMarkerAction implements Action {

    private TextureRegistry textureRegistry = TextureRegistry.INSTANCE;

    private Point coordinates;
    private float phase = 0f;
    private Targetable target;

    public TargetMarkerAction(Targetable target) {
        this.target = target;
    }

    @Override
    public void update() {
        if(phase > 3f)
            phase = 0f;

        RendererToolsRegistry.INSTANCE.getSpriteBatch().draw(textureRegistry.getActionTexture(TargetMarkerAction.class), coordinates.getX(), coordinates.getY() -0.3f, 0.0f, 0.0f, 1, 1, ((WorldObject)target).getWorldMapSize(), ((WorldObject)target).getWorldMapSize(), 0.0f, (int)phase * 32 ,0, 32, 32, false, false);
        phase += Gdx.graphics.getRawDeltaTime();
    }

    @Override
    public void finish() {

    }

    @Override
    public boolean isFinished() {
        return target.isFinished();
    }

    @Override
    public void setCoordinates(Point newCoordinates) {
        this.coordinates = newCoordinates;
    }
}
