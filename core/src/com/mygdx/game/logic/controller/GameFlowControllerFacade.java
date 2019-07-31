package com.mygdx.game.logic.controller;

import com.mygdx.game.logic.GameState;

public class GameFlowControllerFacade implements Controller {

    public static final GameFlowControllerFacade INSTANCE = new GameFlowControllerFacade();

    private GameState gameState = GameState.Sandbox;

    private final SandboxGameLogicController sandboxGameLogicController = SandboxGameLogicController.INSTANCE;
    private final BuildingGameLogicController buildingGameLogicController = BuildingGameLogicController.INSTANCE;

    @Override
    public void update() {
        switch (gameState) {
            case Sandbox:
                sandboxGameLogicController.update();
                break;
            case Builder:
                buildingGameLogicController.update();
                break;
        }
    }

    @Override
    public void togglePause() {
        switch (gameState) {
            case Sandbox:
                sandboxGameLogicController.togglePause();
                break;
            case Builder:
                buildingGameLogicController.togglePause();
                break;
        }
    }

    @Override
    public boolean isPaused() {
        switch (gameState) {
            case Sandbox:
                return sandboxGameLogicController.isPaused();
            case Builder:
                return buildingGameLogicController.isPaused();
        }
        return true;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void toggleGameState() {
        if(gameState.equals(GameState.Sandbox)) {
            gameState = GameState.Builder;
        } else {
            gameState = GameState.Sandbox;
        }
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
