package com.mygdx.game.renderer.selector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.creator.map.Map2D;

import java.util.Random;

public class TileSelector {

    TextureRegion textureRegion;
    long stamp = System.currentTimeMillis();

    public TileSelector() {
        textureRegion = new TextureRegion(new Texture(Gdx.files.internal("tiles/CaveTileset.png")));
    }

    public TextureRegion getFor(Map2D map, int x, int y) {

        if (!map.getTile(x, y).isObstacle()) {

            textureRegion.setRegion(64 + (map.getTileVariation(x,y) % 4)* 16, 0 + (map.getTileVariation(x,y) / 4) * 16, 16, 16);
            return textureRegion;
        }

        int mask = 0;

        if (y + 1 >= map.getHeight() || map.getTile(x, y + 1).isObstacle()) {
            mask += 1;
        }

        if (x + 1 >= map.getWidth() || map.getTile(x + 1, y).isObstacle()) {
            mask += 2;
        }

        if (y - 1 < 0 || map.getTile(x, y - 1).isObstacle()) {
            mask += 4;
        }

        if (x - 1 < 0 || map.getTile(x - 1, y).isObstacle()) {
            mask += 8;
        }

        // rock
        textureRegion.setRegion((mask % 4) * 16, (mask / 4) * 16, 16, 16);
        return textureRegion;
    }

}
