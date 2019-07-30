package com.mygdx.game.builder;

import com.mygdx.game.object.wall.IncompleteWoodenWall;

public class BuilderTool {

    public static final BuilderTool INSTANCE = new BuilderTool();

    public Class<? extends BuildingBlock> getSelectedBlock() {
        return IncompleteWoodenWall.class;
    }

}
