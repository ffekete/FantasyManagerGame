package com.mygdx.game.logic.controller;

import com.mygdx.game.actor.inventory.Inventory;

public class InventoryGameLogicController implements Controller {

    public final static InventoryGameLogicController INSTANCE = new InventoryGameLogicController();

    private Inventory inventory;

    public InventoryGameLogicController() {

    }

    @Override
    public void update() {

    }


    @Override
    public void togglePause() {
    }

    @Override
    public boolean isPaused() {
        return true;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
