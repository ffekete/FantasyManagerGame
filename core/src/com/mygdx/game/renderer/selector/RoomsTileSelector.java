package com.mygdx.game.renderer.selector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.utils.MapUtils;

public class RoomsTileSelector {

    TextureRegion textureRegion;
    long stamp = System.currentTimeMillis();

    public RoomsTileSelector() {
        textureRegion = new TextureRegion(new Texture(Gdx.files.internal("tiles/RoomTileset.png")));
    }

    public TextureRegion getFor(Map2D map, int x, int y) {

        if (!map.getTile(x, y).isObstacle()) {

            textureRegion.setRegion(64 + (map.getTileVariation(x,y) % 4)* 16, 0 + (map.getTileVariation(x,y) / 4) * 16, 16, 16);
            return textureRegion;
        }

        int mask = MapUtils.bitmask4bit(map, x,y);

        // rock
        textureRegion.setRegion((mask % 4) * 16, (mask / 4) * 16, 16, 16);
        return textureRegion;
    }

}
