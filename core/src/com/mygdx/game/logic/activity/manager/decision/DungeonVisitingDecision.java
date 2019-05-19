package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.creator.map.Cluster;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.object.InteractiveObject;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.compound.MoveAndInteractActivity;
import com.mygdx.game.logic.activity.single.InteractActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.registry.WorldMapObjectRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DungeonVisitingDecision implements Decision {
    @Override
    public boolean decide(Actor actor) {

        List<Cluster> clusters = new ArrayList<>();

        for (int i = -10; i <= 10; i++)
            for (int j = -10; j <= 10; j++) {
                if (actor.getX() + i >= 0 && actor.getX() + i <= Config.WorldMap.WORLD_WIDTH / Config.WorldMap.CLUSTER_DIVIDER &&
                        actor.getY() + j >= 0 && actor.getY() + j <= Config.WorldMap.WORLD_HEIGHT / Config.WorldMap.CLUSTER_DIVIDER) {
                    Cluster cluster = new Cluster(actor.getX() + i, actor.getY() + j);
                    clusters.add(cluster);
                }
            }

        for (Cluster cluster : clusters) {

            WorldObject closestObject = null;
            Optional<Set<WorldObject>> optionalWorldObjects = WorldMapObjectRegistry.INSTANCE.getObjects(cluster);
            if(optionalWorldObjects.isPresent()) {
                Optional<WorldObject> optionalWorldObject = optionalWorldObjects.get().stream().filter(object -> InteractiveObject.class.isAssignableFrom(object.getClass()) && distance(object.getCoordinates(), actor.getCoordinates()) < 100).findFirst();
                if(optionalWorldObject.isPresent()   ) {
                    closestObject = optionalWorldObject.get();
                }
            }

            if (Alignment.FRIENDLY.equals(actor.getAlignment())
                    && Map2D.MapType.WORLD_MAP.equals(actor.getCurrentMap().getMapType())
                    && closestObject != null
                    && !actor.getActivityStack().contains(MoveAndInteractActivity.class)) {
                MoveAndInteractActivity moveAndInteractActivity = new MoveAndInteractActivity(Config.Activity.INTERACT_PRIORITY);

                moveAndInteractActivity.add(new MovementActivity(actor, (int)closestObject.getX(), (int)closestObject.getY(), 1, new PathFinder()));
                moveAndInteractActivity.add(new InteractActivity(actor, (InteractiveObject) closestObject));

                actor.getActivityStack().add(moveAndInteractActivity);
                return true;
            } else if(actor.getActivityStack().contains(MoveAndInteractActivity.class)) {
                return true;
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
