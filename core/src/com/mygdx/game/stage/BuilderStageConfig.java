package com.mygdx.game.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.builder.BuilderTool;
import com.mygdx.game.logic.controller.GameFlowControllerFacade;
import com.mygdx.game.object.floor.IncompleteWoodenFloor;
import com.mygdx.game.object.furniture.IncompleteWoodenBed;
import com.mygdx.game.object.wall.IncompleteWoodenDoorWall;
import com.mygdx.game.object.wall.IncompleteWoodenWall;
import com.mygdx.game.registry.RendererToolsRegistry;

public class BuilderStageConfig {

    public static final BuilderStageConfig INSTANCE = new BuilderStageConfig();

    private HorizontalGroup buttonGroup;
    private Table buildElementGroup;

    private boolean hideButtonGroup = false;
    private boolean hideBuildElementGroup = true;

    Drawable buildButtonUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildButton.png")), 0,0,64, 64));
    Drawable buildButtonDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildButton.png")), 64, 0, 64, 64));

    ImageButton buildButton = new ImageButton(buildButtonUp, buildButtonDown);

    public BuilderStageConfig() {
        buttonGroup = new HorizontalGroup().pad(10, 10, 20, 10).bottom().left().wrap(false);
        buttonGroup.addActor(buildButton);

        buildButton.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameFlowControllerFacade.INSTANCE.toggleGameState();
                return true;
            }

        });

        buildElementGroup = new Table().left().pad(0, 0, 300, 0);

        buildElementGroup.layout();

        TextTooltip.TextTooltipStyle textTooltipStyle = new TextTooltip.TextTooltipStyle();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = RendererToolsRegistry.INSTANCE.getBitmapFontSmallest();
        textTooltipStyle.label = labelStyle;

        // Wall
        ImageButton buildWallButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildWallButton.png")), 0, 0, 32, 32)),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildWallButton.png")), 32, 0, 32, 32)));

        buildWallButton.addListener(new TextTooltip("You can build a wall with this.", textTooltipStyle));
        buildWallButton.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                BuilderTool.INSTANCE.setBlockToBuild(IncompleteWoodenWall.class);
                return true;
            }
        });

        // Door
        ImageButton buildDoor = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildWallDoorButton.png")), 0, 0, 32, 32)),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildWallDoorButton.png")), 32, 0, 32, 32)));

        buildDoor.addListener(new TextTooltip("You can build a door with this.", textTooltipStyle));
        buildDoor.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                BuilderTool.INSTANCE.setBlockToBuild(IncompleteWoodenDoorWall.class);
                return true;
            }
        });

        // floor
        ImageButton buildFloor = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/WoodenFloorButton.png")), 0, 0, 32, 32)),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/WoodenFloorButton.png")), 32, 0, 32, 32)));

        buildFloor.addListener(new TextTooltip("You can build a floor with this.", textTooltipStyle));
        buildFloor.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                BuilderTool.INSTANCE.setBlockToBuild(IncompleteWoodenFloor.class);
                return true;
            }

        });

        // bed
        ImageButton buildBed = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/CreateBedButton.png")), 0, 0, 32, 32)),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/CreateBedButton.png")), 32, 0, 32, 32)));

        buildBed.addListener(new TextTooltip("You can build a bed with this.", textTooltipStyle));
        buildBed.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                BuilderTool.INSTANCE.setBlockToBuild(IncompleteWoodenBed.class);
                return true;
            }

        });

        buildElementGroup.add(buildWallButton).width(30).center();
        buildElementGroup.add(buildDoor).width(30).center();
        buildElementGroup.add(buildFloor).width(30).center();
        buildElementGroup.add(buildBed).width(30).center();


    }

    public HorizontalGroup getButtonGroup() {
        return buttonGroup;
    }

    public Table getBuildElementGroup() {
        return buildElementGroup;
    }

    public boolean isHideButtonGroup() {
        return hideButtonGroup;
    }

    public boolean isHideBuildElementGroup() {
        return hideBuildElementGroup;
    }
}
