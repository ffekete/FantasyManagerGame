package com.mygdx.game.renderer.selector;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.object.TileableWallObject;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.utils.MapUtils;

public class WallTileSelector {

    public static final WallTileSelector INSTANCE = new WallTileSelector();

    private final TextureRegistry textureRegistry = TextureRegistry.INSTANCE;

    private TextureRegion textureRegion;

    public WallTileSelector() {

    }

    public TextureRegion getFor(WorldObject[][][] worldObjects, WorldObject worldObject) {

        textureRegion = new TextureRegion(textureRegistry.getForobject(worldObject.getClass()).get(0));

        int mask = MapUtils.bitmask4bit(worldObjects, (int) worldObject.getX(), (int) worldObject.getY(), TileableWallObject.class);

        // rock
        textureRegion.setRegion((mask % 4) * 16, (mask / 4) * 16, 16, 16);
        return textureRegion;
    }

}
