package com.mygdx.game.logic.input.mouse;

import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.input.mouse.processor.BuilderMouseActionProcessor;
import com.mygdx.game.registry.RendererToolsRegistry;

public class BuilderInputController implements Controller {

    public static final BuilderInputController INSTANCE = new BuilderInputController();

    private final BuilderMouseActionProcessor builderMouseActionProcessor = BuilderMouseActionProcessor.INSTANCE;

    private Vector3 screenCoords;
    private Vector3 realWorldCoord;

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenCoords = new Vector3(screenX, screenY, 0);
        realWorldCoord = RendererToolsRegistry.INSTANCE.getCamera().unproject(screenCoords);

        return builderMouseActionProcessor.onClick(Point.of(realWorldCoord));
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        screenCoords = new Vector3(screenX, screenY, 0);
        realWorldCoord = RendererToolsRegistry.INSTANCE.getCamera().unproject(screenCoords);

        return true;
    }
}
