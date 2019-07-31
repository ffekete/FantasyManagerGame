package com.mygdx.game.renderer.selector;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.DungeonType;
import com.mygdx.game.object.TileableObject;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.wall.WoodenWall;
import com.mygdx.game.registry.ObjectRegistry;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.utils.MapUtils;

public class WallTileSelector {

    public static final WallTileSelector INSTANCE = new WallTileSelector();

    private final TextureRegistry textureRegistry = TextureRegistry.INSTANCE;

    private TextureRegion textureRegion;
    private ObjectRegistry objectRegistry = ObjectRegistry.INSTANCE;

    public WallTileSelector() {
        textureRegion = new TextureRegion(textureRegistry.getForobject(WoodenWall.class).get(0));
    }

    public TextureRegion getFor(WorldObject[][] worldObjects, int x, int y) {

        int mask = MapUtils.bitmask4bit(worldObjects, x,y);

        // rock
        textureRegion.setRegion((mask % 4) * 16, (mask / 4) * 16, 16, 16);
        return textureRegion;
    }

}
