package com.mygdx.game.actor.factory;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.creator.map.Map2D;

import java.util.Map;

public interface ActorPlacementStrategy {

    void place(Actor actor, Map2D map);
    Placement X(int x);
    Placement Y(int y);

}
