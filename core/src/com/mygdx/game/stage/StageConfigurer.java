package com.mygdx.game.stage;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.logic.GameState;

import java.util.HashMap;
import java.util.Map;

public class StageConfigurer {

    public static final StageConfigurer INSTANCE = new StageConfigurer();

    private final Map<GameState, Stage> stages = new HashMap<>();

    public StageConfigurer() {
        stages.put(GameState.Sandbox, new Stage());
        stages.put(GameState.Builder, new Stage());
        stages.put(GameState.Inventory, new Stage());
    }

    public void configureButtons() {
        stages.get(GameState.Builder).addActor(BuilderStageConfig.INSTANCE.getButtonGroup());
        stages.get(GameState.Builder).addActor(BuilderStageConfig.INSTANCE.getBuildElementGroup());
        stages.get(GameState.Builder).addActor(BuilderStageConfig.INSTANCE.getFurnitureGroup());
        stages.get(GameState.Builder).addActor(BuilderStageConfig.INSTANCE.getFloorGroup());
        stages.get(GameState.Builder).addActor(BuilderStageConfig.INSTANCE.getWallGroup());
        stages.get(GameState.Builder).addActor(BuilderStageConfig.INSTANCE.getDoorGroup());
        stages.get(GameState.Builder).addActor(BuilderStageConfig.INSTANCE.getRoadGroup());
        stages.get(GameState.Sandbox).addActor(SandboxStageConfig.INSTANCE.getButtonGroup());
    }

    public Stage getFor(GameState gameState) {
        return stages.get(gameState);
    }
}
