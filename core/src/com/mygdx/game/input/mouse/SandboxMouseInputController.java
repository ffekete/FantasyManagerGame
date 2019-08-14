package com.mygdx.game.input.mouse;

import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.input.mouse.processor.SandboxMouseActionProcessor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.registry.RendererToolsRegistry;

public class SandboxMouseInputController implements Controller {

    public static final SandboxMouseInputController INSTANCE = new SandboxMouseInputController();

    private SandboxMouseActionProcessor sandboxMouseActionProcessor = SandboxMouseActionProcessor.INSTANCE;

    private Vector3 screenCoords = new Vector3();
    private Vector3 realWorldCoord;

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenCoords.x = screenX;
        screenCoords.y = screenY;

        realWorldCoord = RendererToolsRegistry.INSTANCE.getCamera().unproject(screenCoords);
        return sandboxMouseActionProcessor.onClick(Point.of(screenX, screenY), Point.of(realWorldCoord), pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
}
