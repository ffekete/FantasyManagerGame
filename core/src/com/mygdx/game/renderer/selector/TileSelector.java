package com.mygdx.game.renderer.selector;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.object.AnimatedTiledObject;
import com.mygdx.game.object.TileableObject;
import com.mygdx.game.object.TileableWallObject;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.utils.MapUtils;

public class TileSelector {

    public static final TileSelector INSTANCE = new TileSelector();

    private final TextureRegistry textureRegistry = TextureRegistry.INSTANCE;

    private TextureRegion textureRegion;

    public TileSelector() {

    }

    public TextureRegion getFor(WorldObject[][][] worldObjects, WorldObject worldObject, Class<? extends TileableObject> clazz) {

        textureRegion = new TextureRegion(textureRegistry.getForobject(worldObject.getClass()).get(0));

        int mask = MapUtils.bitmask4bit(worldObjects, (int) worldObject.getX(), (int) worldObject.getY(), clazz);

        int xOffset = AnimatedTiledObject.class.isAssignableFrom(worldObject.getClass()) ? ((AnimatedTiledObject)worldObject).getPhase() : 0;
        // rock
        textureRegion.setRegion(xOffset * 64 + (mask % 4) * 16, (mask / 4) * 16, 16, 16);
        return textureRegion;
    }

}
