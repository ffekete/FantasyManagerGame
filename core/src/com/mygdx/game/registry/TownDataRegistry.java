package com.mygdx.game.registry;

import com.mygdx.game.logic.Point;

public class TownDataRegistry {

    public static final TownDataRegistry INSTANCE = new TownDataRegistry();

    private final Point townCenter = Point.of(10, 10);
    private int townCentreRadius = 15;

    public Point getTownCenter() {
        return townCenter;
    }

    public int getTownCentreRadius() {
        return townCentreRadius;
    }
}
