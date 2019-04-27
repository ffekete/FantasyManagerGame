package com.mygdx.game.logic.actor;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.pathfinding.PathFinder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActorMovementHandler {

    public static final ActorMovementHandler INSTANCE = new ActorMovementHandler();

    private final Map<Actor, List<PathFinder.Node>> paths;

    public ActorMovementHandler() {
        this.paths = new ConcurrentHashMap<>();
    }

    public void registerActorPath(Actor actor, List<PathFinder.Node> path) {
        paths.put(actor, path);
    }

    public Direction getDirection(Actor actor) {
        Point next = getNextPoint(actor);
        if(next == null) {
            return Direction.LEFT;
        } else {
            return next.getX() < actor.getX() ? Direction.RIGHT : Direction.LEFT;
        }
    }

    public void clearPath(Actor actor) {
        if (paths.containsKey(actor))
            paths.get(actor).clear();
    }

    private Point getNextPoint(Actor actor) {
        if (!paths.containsKey(actor) || paths.get(actor).isEmpty()) {
            return null;
        } else {
            List<PathFinder.Node> path = paths.get(actor);
            PathFinder.Node node = path.get(path.size() - 1);
            return new Point(node.getX(), node.getY());
        }
    }

    public PathFinder.Node getNextPathPoint(Actor actor) {
        if (!paths.containsKey(actor) || paths.get(actor).isEmpty()) {
            return null;
        } else {
            List<PathFinder.Node> path = paths.get(actor);
            return path.get(path.size() - 1);
        }
    }


    public void updateActorOffsetCoordinates(Actor actor, int speed) {
        Point point = getNextPoint(actor);

        if (point != null) {
            if (point.getX() > actor.getX() && point.getY() > actor.getY()) {
                actor.setxOffset(actor.getxOffset() + 1.0f / speed);
                actor.setyOffset(actor.getyOffset() + 1.0f / speed);

            } else if (point.getX() > actor.getX() && point.getY() == actor.getY()) {
                actor.setxOffset(actor.getxOffset() + 1.0f / speed);

            } else if (point.getX() == actor.getX() && point.getY() > actor.getY()) {
                actor.setyOffset(actor.getyOffset() + 1.0f / speed);

            } else if (point.getX() < actor.getX() && point.getY() < actor.getY()) {
                actor.setxOffset(actor.getxOffset() - 1.0f / speed);
                actor.setyOffset(actor.getyOffset() - 1.0f / speed);

            } else if (point.getX() < actor.getX() && point.getY() == actor.getY()) {
                actor.setxOffset(actor.getxOffset() - 1.0f / speed);

            } else if (point.getX() == actor.getX() && point.getY() < actor.getY()) {
                actor.setyOffset(actor.getyOffset() - 1.0f / speed);

            } else if (point.getX() < actor.getX() && point.getY() > actor.getY()) {
                actor.setxOffset(actor.getxOffset() - 1.0f / speed);
                actor.setyOffset(actor.getyOffset() + 1.0f / speed);

            } else if (point.getX() > actor.getX() && point.getY() < actor.getY()) {
                actor.setxOffset(actor.getxOffset() + 1.0f / speed);
                actor.setyOffset(actor.getyOffset() - 1.0f / speed);

            }
        }
    }

    public boolean moveToNextPathPoint(Actor movableActor) {
        List<PathFinder.Node> path = paths.get(movableActor);
        if (path == null || path.isEmpty()) {
            return false;
        }

        PathFinder.Node nextNode = path.get(path.size() - 1);
        if (movableActor.getCurrentMap().getTile(nextNode.getX(), nextNode.getY()).isObstacle()) {
            PathFinder pathFinder = new PathFinder();
            pathFinder.init(movableActor.getCurrentMap());
            path = pathFinder.findAStar(Point.of(movableActor.getX(), movableActor.getY()), Point.of(nextNode.getX(), nextNode.getY()));
        }

        if (path != null && !path.isEmpty()) {
            PathFinder.Node next = path.remove(path.size() - 1);
            movableActor.setCoordinates(next.getX(), next.getY());
        }
        return true;
    }

}
