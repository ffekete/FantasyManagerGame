package com.mygdx.game.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.builder.BuilderTool;
import com.mygdx.game.logic.controller.GameFlowControllerFacade;
import com.mygdx.game.object.floor.IncompleteDirtRoad;
import com.mygdx.game.object.floor.IncompleteStorageAreaFloor;
import com.mygdx.game.object.floor.IncompleteWoodenFloor;
import com.mygdx.game.object.furniture.IncompleteWoodenBed;
import com.mygdx.game.object.wall.IncompleteWoodenDoorWall;
import com.mygdx.game.object.wall.IncompleteWoodenWall;
import com.mygdx.game.registry.RendererToolsRegistry;

public class BuilderStageConfig {

    public static final BuilderStageConfig INSTANCE = new BuilderStageConfig();

    private HorizontalGroup buttonGroup;
    private Table buildElementGroup;
    private Table furnitureGroup;
    private Table floorGroup;
    private Table wallGroup;
    private Table doorGroup;
    private Table roadGroup;

    private boolean hideButtonGroup = false;
    private boolean hideBuildElementGroup = true;

    Drawable buildButtonUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildButton.png")), 0,0,64, 64));
    Drawable buildButtonDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildButton.png")), 64, 0, 64, 64));

    ImageButton buildButton = new ImageButton(buildButtonUp, buildButtonDown);

    public BuilderStageConfig() {
        buttonGroup = new HorizontalGroup().pad(10, 0, 70, 10).bottom().left().wrap(false);
        buttonGroup.addActor(buildButton);

        buildButton.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameFlowControllerFacade.INSTANCE.toggleGameState();
                return true;
            }

        });

        buildElementGroup = new Table().left().pad(0, 0, 300, 0);

        wallGroup = new Table().left().pad(0, 0, 300, 0);
        floorGroup = new Table().left().pad(0, 0, 300, 0);
        furnitureGroup = new Table().left().pad(0, 0, 300, 0);
        doorGroup = new Table().left().pad(0, 0, 300, 0);
        roadGroup = new Table().left().pad(0, 0, 300, 0);
        hideAll();


        buildElementGroup.layout();

        TextTooltip.TextTooltipStyle textTooltipStyle = new TextTooltip.TextTooltipStyle();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = RendererToolsRegistry.INSTANCE.getBitmapFontSmallest();
        textTooltipStyle.label = labelStyle;

        // Back
        ImageButton backButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BackButton.png")), 0, 0, 32, 32)),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BackButton.png")), 32, 0, 32, 32)));

        backButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hideAll();
                return true;
            }
        });

        ImageButton backButton2 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BackButton.png")), 0, 0, 32, 32)),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BackButton.png")), 32, 0, 32, 32)));

        backButton2.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hideAll();
                return true;
            }
        });

        ImageButton backButton3 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BackButton.png")), 0, 0, 32, 32)),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BackButton.png")), 32, 0, 32, 32)));

        backButton3.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hideAll();
                return true;
            }
        });

        ImageButton backButton4 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BackButton.png")), 0, 0, 32, 32)),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BackButton.png")), 32, 0, 32, 32)));

        backButton4.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hideAll();
                return true;
            }
        });

        ImageButton backButton5 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BackButton.png")), 0, 0, 32, 32)),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BackButton.png")), 32, 0, 32, 32)));

        backButton5.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hideAll();
                return true;
            }
        });


        ImageButton furnitureGroupButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildFurnitureButton.png")), 0, 0, 32, 32)),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildFurnitureButton.png")), 32, 0, 32, 32)));
        furnitureGroupButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buildElementGroup.setVisible(false);
                furnitureGroup.setVisible(true);
                return true;
            }
        });

        ImageButton wallsGroupButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildWallsButton.png")), 0, 0, 32, 32)),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildWallsButton.png")), 32, 0, 32, 32)));
        wallsGroupButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buildElementGroup.setVisible(false);
                wallGroup.setVisible(true);
                return true;
            }
        });

        ImageButton floorsGroupButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildFloorsButton.png")), 0, 0, 32, 32)),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildFloorsButton.png")), 32, 0, 32, 32)));
        floorsGroupButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buildElementGroup.setVisible(false);
                floorGroup.setVisible(true);
                return true;
            }
        });

        ImageButton doorsGroupButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildDoorsButton.png")), 0, 0, 32, 32)),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildDoorsButton.png")), 32, 0, 32, 32)));

        doorsGroupButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buildElementGroup.setVisible(false);
                doorGroup.setVisible(true);
                return true;
            }
        });

        ImageButton roadsGroupButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildRoadsButton.png")), 0, 0, 32, 32)),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildRoadsButton.png")), 32, 0, 32, 32)));

        roadsGroupButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buildElementGroup.setVisible(false);
                roadGroup.setVisible(true);
                return true;
            }
        });

        buildElementGroup.add(furnitureGroupButton);
        buildElementGroup.add(floorsGroupButton);
        buildElementGroup.add(wallsGroupButton);
        buildElementGroup.add(doorsGroupButton);
        buildElementGroup.add(roadsGroupButton);

        wallGroup.add(backButton);
        floorGroup.add(backButton2);
        doorGroup.add(backButton3);
        furnitureGroup.add(backButton4);
        roadGroup.add(backButton5);

        // Wall
        ImageButton buildWoodenWallButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildWallButton.png")), 0, 0, 32, 32)),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildWallButton.png")), 32, 0, 32, 32)));

        buildWoodenWallButton.addListener(new TextTooltip("You can build a wall with this.", textTooltipStyle));
        buildWoodenWallButton.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                BuilderTool.INSTANCE.setBlockToBuild(IncompleteWoodenWall.class);
                return true;
            }
        });
        wallGroup.add(buildWoodenWallButton).width(30).center();

        // Door
        ImageButton buildWoodenDoorButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildWallDoorButton.png")), 0, 0, 32, 32)),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildWallDoorButton.png")), 32, 0, 32, 32)));

        buildWoodenDoorButton.addListener(new TextTooltip("You can build a door with this.", textTooltipStyle));
        buildWoodenDoorButton.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                BuilderTool.INSTANCE.setBlockToBuild(IncompleteWoodenDoorWall.class);
                return true;
            }
        });
        doorGroup.add(buildWoodenDoorButton).width(30).center();

        // floor
        ImageButton buildWoodenFloorButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/WoodenFloorButton.png")), 0, 0, 32, 32)),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/WoodenFloorButton.png")), 32, 0, 32, 32)));

        buildWoodenFloorButton.addListener(new TextTooltip("You can build a floor with this.", textTooltipStyle));
        buildWoodenFloorButton.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                BuilderTool.INSTANCE.setBlockToBuild(IncompleteWoodenFloor.class);
                return true;
            }

        });
        floorGroup.add(buildWoodenFloorButton).width(30).center();

        ImageButton buildStorageAreaFloorButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildStorageAreaButton.png")), 0, 0, 32, 32)),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildStorageAreaButton.png")), 32, 0, 32, 32)));

        buildStorageAreaFloorButton.addListener(new TextTooltip("You can build a storage area with this.", textTooltipStyle));
        buildStorageAreaFloorButton.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                BuilderTool.INSTANCE.setBlockToBuild(IncompleteStorageAreaFloor.class);
                return true;
            }

        });
        floorGroup.add(buildStorageAreaFloorButton).width(30).center();

        // bed
        ImageButton buildWoodenBedButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/CreateBedButton.png")), 0, 0, 32, 32)),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/CreateBedButton.png")), 32, 0, 32, 32)));

        buildWoodenBedButton.addListener(new TextTooltip("You can build a bed with this.", textTooltipStyle));
        buildWoodenBedButton.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                BuilderTool.INSTANCE.setBlockToBuild(IncompleteWoodenBed.class);
                return true;
            }

        });
        furnitureGroup.add(buildWoodenBedButton).width(30).center();



        // road
        ImageButton buildDirtRoadButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildDirtRoadButton.png")), 0, 0, 32, 32)),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/button/BuildDirtRoadButton.png")), 32, 0, 32, 32)));

        buildDirtRoadButton.addListener(new TextTooltip("You can build a road with this.", textTooltipStyle));
        buildDirtRoadButton.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                BuilderTool.INSTANCE.setBlockToBuild(IncompleteDirtRoad.class);
                return true;
            }

        });
        roadGroup.add(buildDirtRoadButton).width(30).center();


    }

    private void hideAll() {
        furnitureGroup.setVisible(false);
        wallGroup.setVisible(false);
        doorGroup.setVisible(false);
        floorGroup.setVisible(false);
        roadGroup.setVisible(false);
        buildElementGroup.setVisible(true);
    }

    public HorizontalGroup getButtonGroup() {
        return buttonGroup;
    }

    public Table getBuildElementGroup() {
        return buildElementGroup;
    }

    public Table getFurnitureGroup() {
        return furnitureGroup;
    }

    public Table getFloorGroup() {
        return floorGroup;
    }

    public Table getWallGroup() {
        return wallGroup;
    }

    public Table getDoorGroup() {
        return doorGroup;
    }

    public Table getRoadGroup() {
        return roadGroup;
    }

    public boolean isHideButtonGroup() {
        return hideButtonGroup;
    }

    public boolean isHideBuildElementGroup() {
        return hideBuildElementGroup;
    }
}
