package com.mygdx.game.object.floor;

import com.mygdx.game.Config;
import com.mygdx.game.item.Item;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.StorageArea;
import com.mygdx.game.object.WorldObject;

public class StorageAreaFloor implements Floor, WorldObject, TileableFloorObject, StorageArea {

    private Point coordinates;
    private Class<? extends Item> clazz;
    private int amount;

    public StorageAreaFloor(Point coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public float getX() {
        return coordinates.getX();
    }

    @Override
    public float getY() {
        return coordinates.getY();
    }

    @Override
    public void setCoordinates(Point point) {
        this.coordinates = point;
    }

    @Override
    public Point getCoordinates() {
        return coordinates;
    }

    @Override
    public float getWorldMapSize() {
        return Config.Object.WOODEN_WALL_WORLD_MAP_SIZE;
    }

    @Override
    public Class<? extends Item> getStoredItem() {
        return clazz;
    }

    @Override
    public int getStoredAmount() {
        return amount;
    }

    @Override
    public void setStoredItem(Class<? extends Item> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void setStoredAmount(int amount) {
        this.amount = amount;
    }
}
