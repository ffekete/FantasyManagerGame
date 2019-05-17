package com.mygdx.game.object.factory;

import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.object.WorldObject;

public interface ObjectPlacementStrategy {

    void place(WorldObject object, Map2D map);
    ObjectPlacement X(int x);
    ObjectPlacement Y(int y);

}
