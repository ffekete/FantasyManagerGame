package com.mygdx.game.object;

import com.mygdx.game.object.floor.TileableFloorObject;

import java.util.List;

public interface TileableObject {
    List<Class<? extends TileableObject>> getConnectableTypes();
}
