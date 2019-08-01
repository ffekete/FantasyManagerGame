package com.mygdx.game.builder;

import com.mygdx.game.object.wall.IncompleteWoodenWall;

public class BuilderTool {

    public static final BuilderTool INSTANCE = new BuilderTool();

    private Class<? extends BuildingBlock> blockToBuild = IncompleteWoodenWall.class;

    public Class<? extends BuildingBlock> getSelectedBlock() {
        return blockToBuild;
    }

    public void setBlockToBuild(Class<? extends BuildingBlock> blockToBuild) {
        this.blockToBuild = blockToBuild;
    }
}
