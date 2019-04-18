package com.mygdx.game.actor;

import com.mygdx.game.logic.pathfinding.PathFinder;

import java.util.List;

public interface MovableActor extends Actor, Movable {
    boolean hasPathPointsLeft();
    void setPath(List<PathFinder.Node> nodes);
}
