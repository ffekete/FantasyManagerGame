package com.mygdx.game.object;

import java.util.List;

public interface TileableObject {
    List<Class<? extends TileableObject>> getConnectableTypes();
}
