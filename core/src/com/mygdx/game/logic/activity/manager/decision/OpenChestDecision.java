package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.compound.MoveAndInteractActivity;
import com.mygdx.game.logic.activity.single.InteractActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.InteractiveObject;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.decoration.TreasureChest;
import com.mygdx.game.object.interactive.DungeonEntrance;
import com.mygdx.game.registry.ObjectRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class OpenChestDecision implements Decision {
    @Override
    public boolean decide(Actor actor) {

        if (!actor.getActivityStack().contains(MoveAndInteractActivity.class)) {
            List<Cluster> clusters = new ArrayList<>();

            for (int i = -10; i <= 10; i++)
                for (int j = -10; j <= 10; j++) {
                    if (actor.getX() / Config.WorldMap.CLUSTER_DIVIDER + i >= 0 && actor.getX() / Config.WorldMap.CLUSTER_DIVIDER + i <= Config.WorldMap.WORLD_WIDTH / Config.WorldMap.CLUSTER_DIVIDER &&
                            actor.getY() / Config.WorldMap.CLUSTER_DIVIDER + j >= 0 && actor.getY() / Config.WorldMap.CLUSTER_DIVIDER + j <= Config.WorldMap.WORLD_HEIGHT / Config.WorldMap.CLUSTER_DIVIDER) {
                        Cluster cluster = new Cluster(actor.getX() / Config.WorldMap.CLUSTER_DIVIDER + i, actor.getY() / Config.WorldMap.CLUSTER_DIVIDER + j);

                        if(ObjectRegistry.INSTANCE.getObjects(actor.getCurrentMap(), cluster).isPresent())
                            clusters.add(cluster);
                    }
                }

            for (Cluster cluster : clusters) {

                WorldObject closestObject = null;
                Optional<Set<WorldObject>> optionalWorldObjects = ObjectRegistry.INSTANCE.getObjects(actor.getCurrentMap(), cluster);
                if (optionalWorldObjects.isPresent()) {
                    List<WorldObject> optionalWorldObject = optionalWorldObjects.get().stream()
                            .filter(object -> InteractiveObject.class.isAssignableFrom(object.getClass()) && distance(object.getCoordinates(), actor.getCoordinates()) < 100)
                            .collect(Collectors.toList());

                    if (!optionalWorldObject.isEmpty()) {
                        Optional<TreasureChest> treasureChest = optionalWorldObject.stream().filter(worldObject -> TreasureChest.class.isAssignableFrom(worldObject.getClass()) && (((TreasureChest)worldObject).getSize() > 0 || ((TreasureChest)worldObject).getMoney() > 0) && VisibilityMapRegistry.INSTANCE.getFor(actor.getCurrentMap()).getValue((int)worldObject.getX(), (int)worldObject.getY()).contains(actor)).map(worldObject -> (TreasureChest) worldObject).findFirst();
                        if (treasureChest.isPresent())
                            closestObject = treasureChest.get();
                    }
                }

                if (closestObject != null && (((TreasureChest) closestObject).getSize() > 0 || ((TreasureChest) closestObject).getMoney() > 0)) {
                    ActorMovementHandler.INSTANCE.clearPath(actor);
                    actor.getActivityStack().clear();
                    MoveAndInteractActivity moveAndInteractActivity = new MoveAndInteractActivity(Config.Activity.OPEN_CHEST_PRIORITY);

                    moveAndInteractActivity.add(new MovementActivity(actor, (int) closestObject.getX(), (int) closestObject.getY(), 1, new PathFinder()));
                    moveAndInteractActivity.add(new InteractActivity(actor, (InteractiveObject) closestObject));

                    actor.getActivityStack().add(moveAndInteractActivity);
                    return true;
                } else if (actor.getActivityStack().contains(MoveAndInteractActivity.class)) {
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }

    private double distance(Point p1, Point p2) {
        if (p1 == null || p2 == null)
            return Double.MAX_VALUE;

        int a = Math.abs(p1.getX() - p2.getX());
        int b = Math.abs(p1.getY() - p2.getY());

        return Math.sqrt(a * a + b * b);
    }

}
