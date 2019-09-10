package com.mygdx.game.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.logic.GameState;
import com.mygdx.game.logic.controller.GameFlowControllerFacade;
import com.mygdx.game.logic.controller.InventoryGameLogicController;
import com.mygdx.game.renderer.camera.CameraPositionController;

public class SandboxStageConfig {

    public static final SandboxStageConfig INSTANCE = new SandboxStageConfig();

    private HorizontalGroup buttonGroup;

    private boolean hideButtonGroup = false;

    Drawable inventoryButtonUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/InventoryButton.png")), 0,0,64, 64));
    Drawable inventoryButtonDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/InventoryButton.png")), 64, 0, 64, 64));

    Drawable buildButtonUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildButton.png")), 0,0,64, 64));
    Drawable buildButtonDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildButton.png")), 64, 0, 64, 64));


    ImageButton buildButton = new ImageButton(buildButtonUp, buildButtonDown);
    ImageButton inventoryButton = new ImageButton(inventoryButtonUp, inventoryButtonDown);

    public SandboxStageConfig() {
        buttonGroup = new HorizontalGroup().pad(10, 0, 70, 10).bottom().left().wrap(false);
        buttonGroup.addActor(buildButton);

        buildButton.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameFlowControllerFacade.INSTANCE.toggleGameState();
                return true;
            }

        });

        buttonGroup.addActor(inventoryButton);

        inventoryButton.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (CameraPositionController.INSTANCE.getFocusedOn() != null) {
                    InventoryGameLogicController.INSTANCE.setActor(CameraPositionController.INSTANCE.getFocusedOn());
                    GameFlowControllerFacade.INSTANCE.setGameState(GameState.Inventory);
                    return true;
                }
                return false;
            }
        });
    }

    public void update() {
        if (CameraPositionController.INSTANCE.getFocusedOn() != null) {
            inventoryButton.setVisible(true);
        } else {
            inventoryButton.setVisible(false);
        }
    }

    public HorizontalGroup getButtonGroup() {
        return buttonGroup;
    }

    public boolean isHideButtonGroup() {
        return hideButtonGroup;
    }
}
