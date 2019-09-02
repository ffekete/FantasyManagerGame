package com.mygdx.game.logic.actor;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.ActorRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActorMovementHandler {

    public static final ActorMovementHandler INSTANCE = new ActorMovementHandler();

    private final Map<Actor, List<PathFinder.Node>> paths;
    private final ActorRegistry actorRegistry = ActorRegistry.INSTANCE;

    private Map<Actor, Direction> directions = new HashMap<>();

    private List<Actor> changedCoordList = new ArrayList<>();

    public ActorMovementHandler() {
        this.paths = new ConcurrentHashMap<>();
    }

    public void registerActorPath(Actor actor, List<PathFinder.Node> path) {
        paths.put(actor, path);
    }

    public Direction getDirection(Actor actor) {
        Point next = getNextPoint(actor);
        if (next == null) {
            if(directions.containsKey(actor))
                return directions.get(actor);
            else
                return Direction.UP;
        } else {
            Direction nextDirection;

            if (next.getX() < actor.getX())
                nextDirection = Direction.LEFT;
            else if (next.getX() > actor.getX())
                nextDirection = Direction.RIGHT;
            else if (next.getY() < actor.getY())
                nextDirection = Direction.DOWN;
            else //(next.getY() > actor.getY())
                nextDirection = Direction.UP;

            directions.put(actor, nextDirection);

            return nextDirection;
        }
    }

    public boolean hasPath(Actor actor) {
        return paths.get(actor) != null && !paths.get(actor).isEmpty();
    }

    public void clearPathForAll(Map2D map2D) {
        for(Actor actor : actorRegistry.getActors(map2D)) {
            if (paths.containsKey(actor))
                paths.get(actor).clear();
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
            paths.put(movableActor, path);
        }

        if (path != null && !path.isEmpty()) {
            PathFinder.Node next = path.remove(path.size() - 1);

            ActorRegistry.INSTANCE.remove(movableActor.getCurrentMap(), movableActor, false);
            movableActor.setCoordinates(new Point(next.getX(), next.getY()));
            ActorRegistry.INSTANCE.add(movableActor.getCurrentMap(), movableActor, false);
            changedCoordList.add(movableActor);
            return true;
        }
        return false;
    }

    public List<Actor> getChangedCoordList() {
        return changedCoordList;
    }

    public void updateDirection(Actor actor, Direction direction) {
        directions.put(actor, direction);
    }
}
