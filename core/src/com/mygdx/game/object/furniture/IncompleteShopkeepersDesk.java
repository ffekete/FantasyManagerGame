package com.mygdx.game.object.furniture;

import com.mygdx.game.actor.worker.Shopkeeper;
import com.mygdx.game.builder.BuildingBlock;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.*;
import com.mygdx.game.object.interactive.Anvil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IncompleteShopkeepersDesk implements WorldObject, NoViewBlockinObstacle, Furniture, BuildingBlock, TileableFurnitureObject {

    private Point coordinates;
    private float progress = 0.0f;

    public IncompleteShopkeepersDesk(Point coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public void addProgress(float percentage) {
        this.progress += percentage;
    }

    @Override
    public Class finish() {
        return ShopkeepersDesk.class;
    }

    @Override
    public boolean isFinished() {
        return progress >= 100f;
    }

    @Override
    public float getProgress() {
        return progress;
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
        return 1.f;
    }

    @Override
    public List<Class<? extends TileableObject>> getConnectableTypes() {
        return Arrays.asList(IncompleteShopkeepersDesk.class, ShopkeepersDesk.class);
    }
}
