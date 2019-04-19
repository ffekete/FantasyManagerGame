package com.mygdx.game.logic.actor;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.pathfinding.PathFinder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActorMovementHandler {

    private final PathFinder pathFinder;
    private final Map<Actor, List<PathFinder.Node>> paths;

    public ActorMovementHandler() {
        this.pathFinder = new PathFinder();
        this.paths = new HashMap<>();
    }

    public void registerActorPath(Actor actor, List<PathFinder.Node> path) {
        paths.put(actor, path);
    }

    public boolean moveToNextPathPoint(Actor movableActor) {
        List<PathFinder.Node> path =  paths.get(movableActor);
        if(path == null || path.isEmpty()) {
            return false;
        }

        PathFinder.Node nextNode = path.get(path.size()-1);
        if(movableActor.getCurrentMap().getTile(nextNode.getX(), nextNode.getY()).isObstacle()) {
            pathFinder.init(movableActor.getCurrentMap());
            path = pathFinder.findAStar(Point.of(movableActor.getX(), movableActor.getY()), Point.of(nextNode.getX(), nextNode.getY()));
        }

        if(path != null && !path.isEmpty()) {
            PathFinder.Node next = path.remove(path.size()-1);
            System.out.println(path.size());
            movableActor.setCoordinates(next.getX(), next.getY());
        }
        return true;
    }

}
