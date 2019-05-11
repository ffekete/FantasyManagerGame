package com.mygdx.game.registry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.google.common.collect.ImmutableMap;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.monster.Goblin;
import com.mygdx.game.creator.TileBase;
import com.mygdx.game.creator.map.dungeon.Tile;
import com.mygdx.game.creator.map.object.WorldObject;
import com.mygdx.game.creator.map.object.interactive.DungeonEntrance;
import com.mygdx.game.creator.map.worldmap.WorldMapTile;
import com.mygdx.game.item.food.Bread;
import com.mygdx.game.item.shield.SmallShiled;
import com.mygdx.game.item.weapon.FlameTongue;
import com.mygdx.game.item.weapon.PoisonFang;
import com.mygdx.game.item.weapon.ShortSword;

import java.util.Map;

import javax.xml.soap.Text;

public class TextureRegistry {

    public static final TextureRegistry INSTANCE = new TextureRegistry();

    private Map<Class, Texture> textures;
    private Map<TileBase, Texture> mapTextures;
    private Map<Class<? extends WorldObject>, Texture> objectTextures;

    public TextureRegistry() {
        textures = ImmutableMap.<Class, Texture>builder()
                .put(Warrior.class, new Texture(Gdx.files.internal("warrior.png")))
                .put(Goblin.class, new Texture(Gdx.files.internal("goblin.png")))
                .put(ShortSword.class, new Texture(Gdx.files.internal("sword.png")))
                .put(Bread.class, new Texture(Gdx.files.internal("bread.png")))
                .put(SmallShiled.class, new Texture(Gdx.files.internal("shield.jpg")))
                .put(PoisonFang.class, new Texture(Gdx.files.internal("sword.png")))
                .put(FlameTongue.class, new Texture(Gdx.files.internal("sword.png")))
                .build();

        mapTextures = ImmutableMap.<TileBase, Texture>builder()
                .put(WorldMapTile.GRASS, new Texture(Gdx.files.internal("grass.jpg")))
                .put(Tile.STONE_WALL, new Texture(Gdx.files.internal("wall.png")))
                .put(Tile.FLOOR, new Texture(Gdx.files.internal("terrain.jpg")))
                .put(Tile.EMPTY, new Texture(Gdx.files.internal("void.png")))
                .build();

        objectTextures = ImmutableMap.<Class<? extends WorldObject>, Texture>builder()
                .put(DungeonEntrance.class, new Texture(Gdx.files.internal("DungeonEntrance.jpg")))
                .build();
    }

    public Texture getForobject(Class<? extends WorldObject> objectClass) {
        return objectTextures.get(objectClass);
    }


    public Texture getForTile(TileBase tileBase) {
        return mapTextures.get(tileBase);
    }

    public Texture getFor(Class clazz) {
        if(textures.containsKey(clazz)) {
            return textures.get(clazz);
        }
        return null;
    }

    public void dispose() {
        for(Texture t : textures.values()) {
            t.dispose();
        }
    }
}
