package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.InteractiveObject;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.compound.MoveAndInteractActivity;
import com.mygdx.game.logic.activity.single.InteractActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.object.interactive.DungeonEntrance;
import com.mygdx.game.registry.ObjectRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class DungeonVisitingDecision implements Decision {
    @Override
    public boolean decide(Actor actor) {

        if(actor.isSleeping()) {
            return false;
        }

        if(actor.getActivityStack().contains(MoveAndInteractActivity.class)) {
            return true;
        }

        if(Map2D.MapType.WORLD_MAP.equals(actor.getCurrentMap().getMapType())) {

            for (Cluster cluster : Cluster.ofSurrounding(actor.getCurrentMap(), actor.getX(), actor.getY(), 5)) {

                WorldObject closestObject = null;
                Optional<Set<WorldObject>> optionalWorldObjects = ObjectRegistry.INSTANCE.getObjects(actor.getCurrentMap(), cluster);
                if (optionalWorldObjects.isPresent()) {
                    List<WorldObject> optionalWorldObject = optionalWorldObjects.get().stream().filter(object -> InteractiveObject.class.isAssignableFrom(object.getClass()) && distance(object.getCoordinates(), actor.getCoordinates()) < 100).collect(Collectors.toList());

                    if (!optionalWorldObject.isEmpty()) {
                        Optional<DungeonEntrance> unvisitedDungeonEntrance = optionalWorldObject.stream().filter(worldObject -> DungeonEntrance.class.isAssignableFrom(worldObject.getClass()) && !((DungeonEntrance) worldObject).getTo().areAllLevelsExplored()).map(worldObject -> (DungeonEntrance) worldObject).findFirst();
                        if (unvisitedDungeonEntrance.isPresent())
                            closestObject = unvisitedDungeonEntrance.get();
                    }
                }

                if (Alignment.FRIENDLY.equals(actor.getAlignment())
                        && Map2D.MapType.WORLD_MAP.equals(actor.getCurrentMap().getMapType())
                        && closestObject != null
                        && DungeonEntrance.class.isAssignableFrom(closestObject.getClass())
                        && !((DungeonEntrance) closestObject).getTo().areAllLevelsExplored()) {

                    MoveAndInteractActivity moveAndInteractActivity = new MoveAndInteractActivity(Config.Activity.INTERACT_PRIORITY, MoveAndInteractActivity.class);

                    moveAndInteractActivity.add(new MovementActivity(actor, (int) closestObject.getX(), (int) closestObject.getY(), 1, new PathFinder()));
                    moveAndInteractActivity.add(new InteractActivity(actor, (InteractiveObject) closestObject));

                    actor.getActivityStack().add(moveAndInteractActivity);
                    return true;
                }
            }
        }

        return false;
    }

    private double distance(Point p1, Point p2) {
        if(p1 == null || p2 == null)
            return Double.MAX_VALUE;

        int a = Math.abs(p1.getX() - p2.getX());
        int b = Math.abs(p1.getY() - p2.getY());

        return Math.sqrt(a*a + b*b);
    }

}
