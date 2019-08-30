package com.mygdx.game.input.mouse;

import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.input.mouse.processor.InventoryMouseActionProcessor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.registry.RendererToolsRegistry;

public class InventoryMouseInputController implements Controller {

    public static final InventoryMouseInputController INSTANCE = new InventoryMouseInputController();

    private InventoryMouseActionProcessor inventoryMouseActionProcessor = InventoryMouseActionProcessor.INSTANCE;

    private Vector3 screenCoords = new Vector3();
    private Vector3 realWorldCoord;

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenCoords.x = screenX;
        screenCoords.y = screenY;
        realWorldCoord = RendererToolsRegistry.INSTANCE.getInfoCamera().unproject(screenCoords);
        //return inventoryMouseActionProcessor.onClick(Point.of(screenX, screenY), pointer);
        return inventoryMouseActionProcessor.onClick(Point.of(realWorldCoord), pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        screenCoords.x = screenX;
        screenCoords.y = screenY;
        realWorldCoord = RendererToolsRegistry.INSTANCE.getInfoCamera().unproject(screenCoords);
        return inventoryMouseActionProcessor.onMouseMoved((int)realWorldCoord.x, (int)realWorldCoord.y);
        //return inventoryMouseActionProcessor.onMouseMoved(screenX, screenY);
    }
}
