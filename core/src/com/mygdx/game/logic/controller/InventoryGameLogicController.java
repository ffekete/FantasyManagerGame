package com.mygdx.game.logic.controller;

import com.mygdx.game.actor.Actor;

public class InventoryGameLogicController implements Controller {

    public final static InventoryGameLogicController INSTANCE = new InventoryGameLogicController();

    private Actor actor;

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

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }
}
