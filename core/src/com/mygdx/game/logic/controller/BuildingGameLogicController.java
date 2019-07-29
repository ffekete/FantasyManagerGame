package com.mygdx.game.logic.controller;

public class BuildingGameLogicController implements Controller {

    public static final BuildingGameLogicController INSTANCE = new BuildingGameLogicController();


    @Override
    public void update() {

    }

    @Override
    public void togglePause() {
        // no op
    }

    @Override
    public boolean isPaused() {
        return true;
    }
}
