package com.mygdx.game.input.mouse.processor;

import com.mygdx.game.item.Item;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.controller.InventoryGameLogicController;
import com.mygdx.game.renderer.inventory.InventoryRenderer;

import java.util.Optional;

import static com.mygdx.game.renderer.inventory.InventoryRenderer.*;

public class InventoryMouseActionProcessor {

    public static final InventoryMouseActionProcessor INSTANCE = new InventoryMouseActionProcessor();

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

        getInventoryArea(screenX, screenY).ifPresent(c -> {
            int index = c.getX() + (c.getY()) * INVENTORY_ROW_LENGTH;
            Optional<Item> item = InventoryGameLogicController.INSTANCE.getActor().getInventory().get(index);
            item.ifPresent(item1 -> InventoryRenderer.INSTANCE.setItemText(item1.getName() + "\n\n" + item1.getDescription()));
        });

        return false;
    }


    private Optional<Point> getInventoryArea(int x, int y) {
        if (x < INVENTORY_LEFT_X || x > INVENTORY_RIGHT_X - 30 || y < INVENTORY_RIGHT_Y || y > INVENTORY_LEFT_Y) {
            return Optional.empty();
        }

        return Optional.of(Point.of((x - INVENTORY_LEFT_X) / 64, (INVENTORY_LEFT_Y - y + 15) / 64));
    }
}
