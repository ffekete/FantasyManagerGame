package com.mygdx.game.input.mouse.processor;

import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.controller.InventoryGameLogicController;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.ItemRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.ObjectRegistry;
import com.mygdx.game.renderer.inventory.InventoryRenderer;

public class InventoryMouseActionProcessor {

    public static final InventoryMouseActionProcessor INSTANCE = new InventoryMouseActionProcessor();

    private final ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    private final ObjectRegistry objectRegistry = ObjectRegistry.INSTANCE;
    private final MapRegistry mapRegistry = MapRegistry.INSTANCE;
    private final ItemRegistry itemRegistry = ItemRegistry.INSTANCE;

    public boolean onClick(Point realWorldCoord, int pointer) {
        System.out.println(realWorldCoord.getX() + " " + realWorldCoord.getY());
        return false;
    }

    public boolean onMouseMoved(int screenX, int screenY) {
        if (screenX >= InventoryRenderer.RIGHT_HAND_X - 40 && screenX <= InventoryRenderer.RIGHT_HAND_X + 40 &&
                screenY >= InventoryRenderer.RIGHT_HAND_Y - 40 && screenY <= InventoryRenderer.RIGHT_HAND_Y + 40
                && InventoryGameLogicController.INSTANCE.getActor().getRightHandItem() != null) {

            InventoryRenderer.INSTANCE.setItemText(InventoryGameLogicController.INSTANCE.getActor().getRightHandItem().getName() +
                    "\n\n" + InventoryGameLogicController.INSTANCE.getActor().getRightHandItem().getDescription());
            return true;
        }

        if (screenX >= InventoryRenderer.LEFT_HAND_X - 40 && screenX <= InventoryRenderer.LEFT_HAND_X + 40 &&
                screenY >= InventoryRenderer.LEFT_HAND_Y - 40 && screenY <= InventoryRenderer.LEFT_HAND_Y + 40
                && InventoryGameLogicController.INSTANCE.getActor().getLeftHandItem() != null) {

            InventoryRenderer.INSTANCE.setItemText(InventoryGameLogicController.INSTANCE.getActor().getLeftHandItem().getName() +
                    "\n\n" + InventoryGameLogicController.INSTANCE.getActor().getLeftHandItem().getDescription());
            return true;
        }

        InventoryRenderer.INSTANCE.setItemText(null);

        return false;
    }
}
