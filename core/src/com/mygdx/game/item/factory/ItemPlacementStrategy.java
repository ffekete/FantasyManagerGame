package com.mygdx.game.item.factory;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Item;
import com.mygdx.game.map.Map2D;

public interface ItemPlacementStrategy {

    void place(Item actor, Map2D map);
    Placement X(int x);
    Placement Y(int y);

}
